<?php
require('vendor/autoload.php');
// this will simply read AWS_ACCESS_KEY_ID and AWS_SECRET_ACCESS_KEY from env vars
$s3 = Aws\S3\S3Client::factory();
$bucket = getenv('S3_BUCKET')?: die('No "S3_BUCKET" config var in found in env!');

if($_SERVER['REQUEST_METHOD'] == 'POST' && isset($_FILES['userfile']) && $_FILES['userfile']['error'] == UPLOAD_ERR_OK && is_uploaded_file($_FILES['userfile']['tmp_name'])) {
    // FIXME: add more validation, e.g. using ext/fileinfo

    $file_type = $_FILES['userfile']['type']; //returns the mimetype
    $allowed = array("image/jpeg", "image/jpg", "application/msword", "image/png", "application/vnd.ms-powerpoint", "application/pdf", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
    if(!in_array($file_type, $allowed)) {
    	$error_message = 'Upload Failed. Only .doc .docx .pdf files are accpted.';
    }
    else {
    	try {
    		$newname = $_FILES['userfile']['name'];
    		$string = preg_replace('/\s+/', '', $newname);
        	$upload = $s3->upload($bucket, $string, fopen($_FILES['userfile']['tmp_name'], 'rb'), 'public-read');
        	echo $string;
    	} catch(Exception $e) {
    	}
	}
}
?>