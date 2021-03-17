<?php
include '../Database/login.php';
require_once 'utils.php';
require_once '../config/app.php';

$db = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_BASE);
if ($db->connect_error) {
    die("Connection failed: " . $db->connect_error);
}
if (isset($_POST['remove_button'])) {
    $friend_to_remove = clear_xss($_POST['remove_button']);
    $sql = "DELETE FROM friends WHERE id=?";
    $stmt = $db->prepare($sql);
    $stmt->bind_param('i', $friend_to_remove);
    if ($stmt->execute()) {
        $stmt->close();
        redirect('../Views/friends.php');
        echo "Remove succesfully";
        exit(0);
    } else {
        echo "ERROR, could not remove";
    }
}

$db -> close();
