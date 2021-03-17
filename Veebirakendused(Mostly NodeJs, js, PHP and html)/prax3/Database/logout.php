<?php
require_once 'utils.php';
require_once '../config/app.php';

session_destroy();
unset($_SESSION['id']);
unset($_SESSION['username']);
unset($_SESSION['email']);
unset($_SESSION['verify']);
redirect('../index.php');
