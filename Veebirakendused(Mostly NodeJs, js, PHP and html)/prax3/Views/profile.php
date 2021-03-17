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
        <link rel="stylesheet" href="../Styling/input.css">
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
        <img src="<?php echo $_SESSION['photo'] ?>" alt="Image">
        <h3><?php echo $_SESSION['name'] ?></h3>
        <h3><?php echo $_SESSION['email'] ?></h3>
        <div class="text">
        </div>
        <div class="text">
            <h3>From:</h3>
            <p><?php echo $_SESSION['location'] ?></p>
            <h3>Your description:</h3>
            <p><?php echo $_SESSION['description'] ?></p><br>
        </div>
        <form method="post">
            <label for="location" style="color: #351047">Change location:</label><br>
            <input type="text" id="location" name="location" placeholder="Location.." value=""><br>
            <label for="description" style="color: #351047">Change description:</label><br>
            <textarea class="textarea grey" id="description" name="description" placeholder="Description.."></textarea><br>
            <input class="b_button" type="submit" value="Update"/>
        </form>
        <?php
            $db = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_BASE);
            $user_id = $_SESSION['id'];
            if (!empty($_REQUEST['location'])) {
                $location = clear_xss($_REQUEST['location']);
                $_SESSION['location'] = $location;
                $sql = "UPDATE users SET location=? WHERE id=?";
                $stmt = $db->prepare($sql);
                $stmt->bind_param('si', $location, $user_id);
                if ($stmt->execute()) {
                    $stmt->close();
                    redirect('../Views/profile.php');
                }
            }
            if (!empty($_REQUEST['description'])) {
                $user_id = $_SESSION['id'];
                $description = clear_xss($_REQUEST['description']);
                $_SESSION['description'] = $description;
                $sql_desc = "UPDATE users SET description=? WHERE id=?";
                $stmt_desc = $db->prepare($sql_desc);
                $stmt_desc->bind_param('si', $description, $user_id);
                if ($stmt_desc->execute()) {
                    $stmt_desc->close();
                    redirect('../Views/profile.php');
                }
            }
            ?>
    </div>
</html>
