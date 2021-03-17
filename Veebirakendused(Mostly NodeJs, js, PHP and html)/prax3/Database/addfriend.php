<?php
include '../Database/login.php';
require_once 'utils.php';
require_once '../config/app.php';

$db = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_BASE);
if ($db->connect_error) {
    die("Connection failed: " . $db->connect_error);
}

if (isset($_POST['add_button'])) {
    $newfriend = clear_xss($_POST['add_button']);
    $adder = $_SESSION['id'];
    $sql = "INSERT INTO friends (user_id_1, user_id_2)
    VALUES (?, ?)";
    $stmt = $db->prepare($sql);
    $stmt->bind_param('ii', $adder, $newfriend);
    if ($stmt->execute()) {
        $stmt->close();
        redirect('../Views/friends.php');
        echo "Added succesfully";
        exit(0);
    } else {
        echo "ERROR, could not add";
    }
}

$db -> close();
