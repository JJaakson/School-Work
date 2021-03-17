<?php

function clear_xss($input) {
    return htmlspecialchars($input);
}

function redirect($url) {
    header('location: ' . $url);
}

function friends_result($db, $user_1, $user_2) {
    $sql_fr_1 = "SELECT * FROM friends WHERE user_id_1=?
                                and user_id_2=? LIMIT 1";
    $stmt_fr_1 = $db->prepare($sql_fr_1);
    $stmt_fr_1->bind_param('ii', $user_1, $user_2);
    $stmt_fr_1->execute();
    $result = $stmt_fr_1->get_result();
    $stmt_fr_1->close();
    return $result;
}

function get_user($db, $user_id) {
    $sql = "SELECT name FROM users WHERE id=? LIMIT 1";
    $stmt = $db->prepare($sql);
    $stmt->bind_param('i', $user_id);
    $stmt->execute();
    $result = $stmt->get_result()->fetch_assoc();
    $stmt->close();
    return $result;
}

function get_reactions_array($db, $post_id) {
    $sql = "SELECT COUNT(*) FROM reactions WHERE post_id=?";
    $stmt = $db->prepare($sql);
    $stmt->bind_param('i', $post_id);
    $stmt->execute();
    $result = $stmt->get_result()->fetch_array();
    $stmt->close();
    return $result;
}
