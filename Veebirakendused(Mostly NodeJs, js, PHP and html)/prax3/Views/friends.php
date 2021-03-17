<?php include '../Database/login.php';
require_once '../Database/utils.php';
require_once '../config/app.php';
// redirect user to login page if they're no logged in
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
        <a href="posts.php">Posts</a>
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
        $sql = "SELECT * FROM friends";
        $stmt = $db->prepare($sql);
        if ($stmt->execute()) {
            $result = $stmt->get_result();
            $stmt->close();
            while ($row = $result->fetch_assoc()) {
                $id = "";
                if ($row['user_id_1'] == $_SESSION['id']) {
                    $id = $row['user_id_2'];
                } elseif ($row['user_id_2'] == $_SESSION['id']) {
                    $id = $row['user_id_1'];
                }
                if (!empty($id)) {
                    $friend = get_user($db, $id); ?>
                    <div class="user">
                        <form class="grey" action="../Database/removefriend.php" method="post">
                            <label class="grey" for="remove_button"><?php echo $friend['name'] ?></label>
                            <button class="button" name="remove_button" value="<?php echo $row['id'] ?>" type="submit">Remove Friend!</button>
                        </form>
                    </div>
        <?php
                }
            }
        }
        ?>
    </div>
</html>
