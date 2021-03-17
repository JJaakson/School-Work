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
    <a href="friends.php">Friends</a>
    <div class="footer">
        <a href="../Database/logout.php">Log out!</a>
    </div>
</div>
<div class="content">
    <?php
    $db = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_BASE);
    if ($db->connect_error) {
        die("Connection failed: " . $db->connect_error);
    }
    $user_id = $_SESSION['id'];
    $sql = "SELECT * FROM posts WHERE user_id=? ORDER BY created_at DESC";
    $stmt = $db->prepare($sql);
    $stmt->bind_param('i', $user_id);
    if($stmt->execute()) {
        $result = $stmt->get_result();
        $stmt->close();
        while ($row = $result->fetch_assoc()) {
            $post_id = $row['id'];
            $size_result = get_reactions_array($db, $post_id);
?>
            <div class="post">
                <h3 class="grey"><?php echo $row['content'] ?></h3>
                <p class="grey">Like count: <?php echo $size_result[0] ?></p>
                <form class="grey" action="comment.php" method="post">
                    <button class="button" name="comment_button" value="<?php echo $post_id ?>" type="submit">View comments!</button>
                </form>
            </div>
            <?php
        }
    }
    ?>
</div>
</html>
