<?php
include '../Database/login.php';
require_once 'utils.php';
require_once '../config/app.php';

$db = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_BASE);
if ($db->connect_error) {
    die("Connection failed: " . $db->connect_error);
}

if (isset($_POST['comment_button'])) {
    $post_id = $_SESSION['post_id'];
    $content = clear_xss($_POST['add_comment']);
    $user_id = $_SESSION['id'];
    $sql = "INSERT INTO comments (user_id, post_id, content) VALUES (?, ?, ?)";
    $stmt = $db->prepare($sql);
    $stmt->bind_param('iis', $user_id, $post_id, $content);
    if ($stmt->execute()) {
        $stmt->close();
        redirect("../Views/comment.php");
        echo "Added succesfully";
        exit(0);
    } else {
        echo "ERROR, could not add";
    }
}

$db -> close();
