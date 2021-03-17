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
        <a href="profile.php">Profile</a>
        <a href="create.php">Create a post</a>
        <div class="footer">
            <a href="../Database/logout.php">Log out!</a>
        </div>
    </div>
    <div class="content">
        <form method="post">
            <label>
                <input type="text" placeholder="Search.." name="input">
            </label>
            <button class="b_button" type="submit">Search</button>
        </form>
        <?php
            $host = 'localhost';
            $user = 'root';
            $password = '';
            $base = 'prax3';

            $db = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_BASE);

            if (!empty($_REQUEST['input'])) {

                $user_id = $_SESSION['id'];
                $sql_all = "SELECT * FROM users";
                $stmt = $db->prepare($sql_all);
                $stmt->execute();
                $result = $stmt->get_result();
                $stmt->close();
                $input = clear_xss(trim($_REQUEST['input']));
                if ($result->num_rows > 0) {
                    while ($row = $result->fetch_assoc()) {
                        if ($row['id'] != $_SESSION['id']) {
                            if (strpos(strtolower($row['name']), strtolower($_REQUEST['input'])) !== false
                                || strpos(strtolower($row['location']), strtolower($_REQUEST['input'])) !== false) {
                                $friend_id = $row['id'];
                                $result_fr_1 = friends_result($db, $user_id, $friend_id);
                                $result_fr_2= friends_result($db, $friend_id, $user_id);
                                if($result_fr_1->num_rows == 0 && $result_fr_2->num_rows == 0) {
                                    ?>
                                        <div class="user">
                                            <form class="grey" action="../Database/addfriend.php" method="post">
                                                <label class="grey" for="add_button"><?php echo $row['name'] ?> </label>
                                                <button class="button" name="add_button" value="<?php echo $row['id'] ?>" type="submit">Add Friend!</button>
                                            </form>
                                        </div>
            <?php
                                    }
                                }
                            }
                        }
                    } else { ?>
            <p>No results!</p>
                <?php
                    }
                }
                ?>
    </div>
</html>
