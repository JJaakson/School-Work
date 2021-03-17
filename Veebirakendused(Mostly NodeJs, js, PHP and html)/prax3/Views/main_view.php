<?php include '../Database/login.php';
require_once '../Database/utils.php';
require_once '../config/app.php';
// redirect user to login page if they're not logged in
if (empty($_SESSION['id'])) {
    redirect('login.php');
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
        <a href="profile.php">Profile</a>
        <a href="search.php">Search</a>
        <a href="create.php">Create a post</a>
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
        $sql = "SELECT * FROM posts ORDER BY created_at DESC";
        $stmt = $db->prepare($sql);
        $stmt->execute();
        $result = $stmt->get_result();
        $stmt->close();
        if($result->num_rows > 0) {
            while ($row = $result->fetch_assoc()) {
                $poster_id = $row['user_id'];
                if ($poster_id != $user_id) {
                    $result_fr_1 = friends_result($db, $user_id, $poster_id);
                    $result_fr_2= friends_result($db, $poster_id, $user_id);
                    if($result_fr_1->num_rows != 0 || $result_fr_2->num_rows != 0) {
                        $post_id = $row['id'];
                        $size_result = get_reactions_array($db, $post_id);
                        $poster_result = get_user($db, $poster_id);
                        ?>
                            <div class="post">
                                <h3 class="grey"><?php echo $row['content'] ?></h3><br>
                                <p class="grey">Author: <?php echo  $poster_result['name'] ?>, Like count: <?php echo $size_result[0] ?></p>
                                <form class="grey" action="../Database/reaction.php" method="post">
                                    <button class="button" name="reaction_button" value="<?php echo $post_id ?>" type="submit">Like!</button>
                                </form>
                                <form class="grey" action="comment.php" method="post">
                                    <button class="button" name="comment_button" value="<?php echo $post_id ?>" type="submit">View comments!</button>
                                </form>
                            </div>
        <?php
                    }
                }
            }
        }
        ?>
    </div>
</html>
