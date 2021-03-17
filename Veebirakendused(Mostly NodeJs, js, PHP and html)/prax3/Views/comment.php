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
    <a href="posts.php">Posts</a>
    <a href="friends.php">Friends</a>
    <div class="footer">
        <a href="../Database/logout.php">Log out!</a>
    </div>
</div>
<div class="content">
    <?php
    if (isset($_POST['comment_button'])) {
        unset($_SESSION['post_id']);
        $_SESSION['post_id'] = clear_xss($_REQUEST['comment_button']);
    }
    $db = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_BASE);
    if ($db->connect_error) {
        die("Connection failed: " . $db->connect_error);
    }
    $user_id = $_SESSION['id'];
    $post_id = $_SESSION['post_id'];
    $sql = "SELECT * FROM posts WHERE id=? ORDER BY created_at DESC LIMIT 1";
    $stmt = $db->prepare($sql);
    $stmt->bind_param('i', $post_id);
    $stmt->execute();
    $result = $stmt->get_result()->fetch_assoc();
    $stmt->close();
    $poster_id = $result['user_id'];
    $poster_result = get_user($db, $poster_id);
    ?>
    <div class="post">
        <h3 class="grey"><?php echo $result['content'] ?></h3>
        <p class="grey">Author: <?php echo  $poster_result['name'] ?></p>
        <h4 class="grey" style="color: #351047">Kommentaarid</h4>
    <?php
    $sql_comments = "SELECT * FROM comments WHERE post_id=?";
    $stmt = $db->prepare($sql_comments);
    $stmt->bind_param('i', $post_id);
    $stmt->execute();
    $comments_result = $stmt->get_result();
    $stmt->close();
    while ($comment = $comments_result->fetch_assoc()) {
        $commenter_id = $comment['user_id'];
        $commenter_result = get_user($db, $commenter_id);
        ?>
            <p class="grey" style="color: #351047; margin: 10px"> <?php echo $commenter_result['name'] . ': ' . $comment['content'] ?> </p>
                <?php
                }
                ?>
            </div>
    <form action="../Database/addcomment.php" method="post">
        <textarea class="textarea grey" id="add_comment" name="add_comment" placeholder="Add a comment.."></textarea><br>
        <input class="b_button" name="comment_button" type="submit" value="Add!">
    </form>
</div>
</html>
