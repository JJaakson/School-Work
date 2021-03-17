<?php
require_once 'utils.php';
require_once '../config/app.php';

echo DB_HOST;
echo DB_USER;
echo DB_PASS;
echo DB_BASE;

$db = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_BASE);
if ($db->connect_error) {
    die("Connection failed: " . $db->connect_error);
}
if (isset($_POST['register_button'])) {
	$name = clear_xss(trim($_POST['name']));
	$email = clear_xss(trim($_POST['email']));
	$password = password_hash(clear_xss(trim($_POST['password'])), PASSWORD_DEFAULT);
    if (!empty($name) && !empty($email) && !empty($password)) {
        $description = "Insert below";
        $location = "Insert below";
        $photo = "../Images/profile_picture.png";
        $sql = "INSERT INTO users (name, email, password, description, location, photo) 
    VALUES (?, ?, ?, ?, ?, ?)";
        $stmt = $db->prepare($sql);
        $stmt->bind_param('ssssss', $name, $email, $password, $description, $location, $photo);
        if ($stmt->execute()) {
            $stmt->close();
            redirect('../Views/login_view.php');
            echo "Added succesfully.";
        }
    } else {
        redirect('../Views/register_view.php');
        echo "ERROR, could not add";
    }
}


$db -> close();
