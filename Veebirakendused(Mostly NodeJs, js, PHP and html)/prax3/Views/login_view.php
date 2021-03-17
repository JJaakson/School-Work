<!DOCTYPE html>
<html>
	<head>
		<title>ChattersFeed</title>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="../Styling/stiil.css">
		<link rel="stylesheet" href="../Styling/input.css">
	</head>
	<div class="sidebar">
        <a href="../index.php">Return!</a>
	</div>
	<div class="content">
		<form action="../Database/login.php" method="post">
			<label for="email">Email:</label><br>
			<input type="email" id="email" name="email" placeholder="Email.." value=""><br>
			<label for="password">Password:</label><br>
			<input type="password" id="password" name="password" placeholder="Password.." value=""><br><br>
			<input class="button" name="login_button" type="submit" value="Log in!">
		</form>
	</div>
</html>
