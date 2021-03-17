<?php

if ('127.0.0.1' == $_SERVER['REMOTE_ADDR']) {
    define('DB_HOST', 'localhost');
    define('DB_USER', 'root');
    define('DB_PASS', '');
    define('DB_BASE', 'prax3');
} else {
    define('DB_HOST', 'localhost');
    define('DB_USER', 's_jjaaks');
    define('DB_PASS', 'jUiDcAhl');
    define('DB_BASE', 'jjaaks');
}

