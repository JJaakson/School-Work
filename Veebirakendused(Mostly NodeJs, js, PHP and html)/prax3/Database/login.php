<?php
session_start();
require_once 'utils.php';
require_once '../config/app.php';
$db = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_BASE);
if ($db->connect_error) {
    die("Connection failed: " . $db->connect_error);
}
if (isset($_POST['login_button'])) {
    $email = clear_xss(trim($_POST['email']));
    $password = clear_xss(trim($_POST['password']));
    $sql = "SELECT * FROM users WHERE email=? or name=? LIMIT 1";
    $stmt = $db->prepare($sql);
    $stmt->bind_param('ss', $email, $password);
    if ($stmt->execute()) {
        $result = $stmt->get_result();
        $user = $result->fetch_assoc();
        if (password_verify($password, $user['password'])) {
            $stmt->close();
            $_SESSION['id'] = $user['id'];
            $_SESSION['name'] = $user['name'];
            $_SESSION['email'] = $user['email'];
            $_SESSION['location'] = $user['location'];
            $_SESSION['description'] = $user['description'];
            $_SESSION['photo'] = $user['photo'];
            var_dump("Login successful");
            header('location: ../Views/main_view.php');
            exit(0);
        } else {
            header('location: ../Views/login_view.php');
            var_dump("password failed");
        }
    }
}

$db -> close();
