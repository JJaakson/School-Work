<?php
include '../Database/login.php';
require_once 'utils.php';
require_once '../config/app.php';

$db = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_BASE);
if ($db->connect_error) {
    die("Connection failed: " . $db->connect_error);
}

if (isset($_POST['reaction_button'])) {
    $post_id = clear_xss($_POST['reaction_button']);
    $user_id = $_SESSION['id'];
    $sql = "SELECT * FROM reactions WHERE user_id=? and post_id=? LIMIT 1";
    $stmt = $db->prepare($sql);
    $stmt->bind_param('ii', $user_id, $post_id);
    $stmt->execute();
    $result = $stmt->get_result();
    $stmt->close();
    if ($result->num_rows != 0) {
        $reaction_id = $result->fetch_assoc()['id'];
        $sql_del = "DELETE FROM reactions WHERE id=?";
        $stmt_del = $db->prepare($sql_del);
        $stmt_del->bind_param('i', $reaction_id);
        if ($stmt_del->execute()) {
            $stmt_del->close();
            redirect('../Views/main_view.php');
            echo "Remove succesfully";
            exit(0);
        } else {
            echo "ERROR, could not remove";
        }
    } else {
        $sql_ins = "INSERT INTO reactions (user_id, post_id) VALUES (?, ?)";
        $stmt_ins = $db->prepare($sql_ins);
        $stmt_ins->bind_param('ii',$user_id, $post_id);
        if ($stmt_ins->execute()) {
            $stmt_ins->close();
            redirect('../Views/main_view.php');
            echo "Added succesfully";
            exit(0);
        } else {
            echo "ERROR, could not add";
        }
    }
}

$db -> close();
