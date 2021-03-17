<?php include '../Database/login.php';
require_once '../Database/utils.php';
require_once '../config/app.php';
// redirect user to login page if they're not logged in
if (empty($_SESSION['id'])) {
    redirect('login_view.php');
}
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>ChattersFeed</title>
    <link rel="stylesheet" href="../Styling/stiil.css">
</head>
<div class="sidebar">
    <a href="main_view.php">Feed</a>
    <a href="profile.php">Profile</a>
    <a href="search.php">Search</a>
    <div class="footer">
        <a href="../Database/logout.php">Log out!</a>
    </div>
</div>
<div class="content">
    <form>
        <br>
        <textarea class="textarea grey" id="newpost" name="newpost" placeholder="New posts content.." style="height:200px"></textarea><br>
        <button class="b_button" type="submit">Post!</button>
    </form>
    <?php
        $db = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_BASE);
        if ($db->connect_error) {
            die("Connection failed: " . $db->connect_error);
        }
        if (!empty($_REQUEST['newpost'])) {
            $user_id = $_SESSION['id'];
            $content = clear_xss($_REQUEST['newpost']);
            $sql = "INSERT INTO posts (user_id, content) VALUES (?, ?)";
            $stmt = $db->prepare($sql);
            $stmt->bind_param('is', $user_id, $content);
            if ($stmt->execute()) {
                $stmt->close();
                redirect('../Views/posts.php');
            }
        }
        ?>
</div>
</html>
