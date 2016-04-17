<html>
<head>
    <meta charset="utf-8">
    <title>ICL Weekly Summary Submission</title>
    <link rel="icon" href="favicon.ico" type="image/x-icon" />
    <link rel="stylesheet" href="css/style.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">    
</head>
<body>
<script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>
<script>
var _validFileExtensions = [".pdf", ".png", ".doc", ".docx"];
function validateExtension(oInput){
    var sFileName = oInput.value;
    for (var i = 0; i < _validFileExtensions.length; i++){
        var sCurExtension = _validFileExtensions[i];
        if (sFileName.substr(sFileName.length - sCurExtension.length).toLowerCase() == sCurExtension.toLowerCase()) {
            return true;
        }
    }
    alert("Wrong File. Only .doc .docx .pdf files are accepted");
    oInput.value="";
    return false;
}
</script>
<script>
function validateID(oInput){
    var id_field = oInput.value;
    if (id_field.length != 9) {
        alert("Wrong ID, Please Re-Enter");
        oInput.value="";
        return false;
    }
    if (id_field.charAt(1) == "0" || id_field.charAt(1) == "9") {
        var temp = id_field.charAt(0).toLowerCase();
        if (temp == "a" || temp == "b" || temp == "t")
            return true;
    }
    alert("Wrong ID, Please Re-Enter");
    oInput.value="";
    return false;
}
</script>
<h1></h1>
<iframe src="#" id="no-target" name="no-target" width="30" height="30" style="visibility:hidden"></iframe>
<form enctype="multipart/form-data" id="input-form" action="" method="POST" target="no-target">
    <h1>ICL Weekly Summary Submission</h1>
    <brb>
    <pp1>International Companions for Learning</pp1><br>
    <pp1>國際學伴計畫</pp1><br>
    <br>
    <div class="block">
    <pp2>計畫主持人 :</pp2><pp2 class="space">吳俊輝 教授 (臺灣大學 副國際長)</pp2><brr>
    <pp2>計畫主管機關 :</pp2><pp2 class="space">教育部國教署</pp2><brrr>
    <pp2>Director :</pp2><pp2 class="space">Professor Jiun-Huei Proty Wu</pp2><brr>
    <pp2 class="space">(Deputy Vice President for International Affairs)</pp2><brr><br>
    <pp2>Administration :</pp2><pp2 class="space">Taiwan Ministry of Education</pp2><brrr>
    <pp2>ICL Contact E-mail :</pp2><pp2 class="space"><a href="mailto:service.icl.tw@gmail.com">service.icl.tw@gmail.com</a> </pp2><brr>
    <pp2>Teaching Assistant :</pp2><pp2 class="space">George Wang 王聲融 <a href="mailto:b02902122@ntu.edu.tw">b02902122@ntu.edu.tw</a> </pp2> <brr>
    </div>
    <brb>
    <p class="inputfield"><label for="student-id">Student ID</label></p>
    <brrr>
    <input type="text" id="input-q1" name="q1" placeholder="Required" style="color: #fff;" required tabindex="1" onchange="validateID(this);"/>
    <brb>

    <p class="inputfield"><label for="week#">NTU Semester Week</label></p>
    <brrr>

    <div class="semester-week">
      <div>
        <input type="radio" name="week" id="input-q2" value="Week4" required><label for="Week4"><span><span></span></span> Week 4 (3/14-3/18)</label>
      </div>
      <div>
        <input type="radio" name="week" id="input-q2" value="Week5"><label for="Week5"><span><span></span></span> Week 5 (3/21-3/25)</label>
      </div>
      <div>
        <input type="radio" name="week" id="input-q2" value="Week6"><label for="Week6"><span><span></span></span> Week 6 (3/28-3/31)</label>
      </div>
      <div>
        <input type="radio" name="week" id="input-q2" value="Week7"><label for="Week7"><span><span></span></span> Week 7 (4/06-4/08)</label>
      </div>
      <div>
        <input type="radio" name="week" id="input-q2" value="Week8"><label for="Week8"><span><span></span></span> Week 8 (4/11-4/15)</label>
      </div>
    </div>
    <brb>
    <p class="inputfield">Asking for Week-Off</p>
    <brrr>
    <div class="miss-class">
        <div>
            <b2b><input type="checkbox" name="weekoff" id="input-q6" value="YES"><label for="weekoff"><span><span></span></span>  Select, if you need to miss a session.</label></b2b>
            <b2b><label for="weekoff"><span><span></span></span>     Please provide an explanation in the comment section below.</label></b2b>
            <b2b><label for="weekoff"><span><span></span></span>     And make sure to email your school as well.</label></b2b>
            
        </div>
    </div>
    <brb>
    <p class="inputfield"><label>Skype Video-Calling Quality</label></p>
    <p1 class="inputfield">(optional)</p1>
    <div class="skype-quality">
        Terrible
        <label class="radio">
            <div ><label for="weekoff"><span><span></span></span>1</label></div>
            <div><input type="radio" name="skype-quality" id="input-q3" value="1" /></div>
        </label>
        <label class="radio">
            <div ><label for="weekoff"><span><span></span></span>2</label></div>
            <div><input type="radio" name="skype-quality" id="input-q3" value="2" /></div>
        </label>
        <label class="radio">
            <div ><label for="weekoff"><span><span></span></span>3</label></div>
            <div><input type="radio" name="skype-quality" id="input-q3" value="3" /></div>
        </label>
        <label class="radio">
            <div ><label for="weekoff"><span><span></span></span>4</label></div>
            <div><input type="radio" name="skype-quality" id="input-q3" value="4" /></div>
        </label>
        <label class="radio">
            <div ><label for="weekoff"><span><span></span></span>5</label></div>
            <div><input type="radio" name="skype-quality" id="input-q3" value="5" /></div>
        </label>
        <label class="radio">
            <div ><label for="weekoff"><span><span></span></span>6</label></div>
            <div><input type="radio" name="skype-quality" id="input-q3" value="6" /></div>
        </label>
        <label class="radio">
            <div ><label for="weekoff"><span><span></span></span>7</label></div>
            <div><input type="radio" name="skype-quality" id="input-q3" value="7" /></div>
        </label>
        <label class="radio">
            <div ><label for="weekoff"><span><span></span></span>8</label></div>
            <div><input type="radio" name="skype-quality" id="input-q3" value="8" /></div>
        </label>
        <label class="radio">
            <div ><label for="weekoff"><span><span></span></span>9</label></div>
            <div><input type="radio" name="skype-quality" id="input-q3" value="9" /></div>
        </label>
        <label class="radio">
            <div ><label for="weekoff"><span><span></span></span>10</label></div>
            <div><input type="radio" name="skype-quality" id="input-q3" value="10" /></div>
        </label>
        <label class="radio">Great</label>
   </div>
    <p class="inputfield"><label>Comments</label></p>
    <brb>
    <textarea rows="10" cols="10" id="input-q5" placeholder="All Comments Are Welcome" name="input-q5"></textarea>
    <brb>
    <p class="inputfield">Select Your File
    <div>only .doc .docx .pdf files are accepted</div>
    <div><div><p1p class="inputfield">Please save it as follows:</p1p></div></div>
    <b2b>"name_student-id_week#", Example: George_b01234567_week5</b2b><brb>
    <div><input name="userfile" type="file" onchange="validateExtension(this);" /></div>
    <brb>
    <div>("On successful submission, you will be directed to a new thank-you page.")</div>
    <div>("If you are not at the new thank-you page, please check your file and re-submit")</div>
    
    <input name="submit" type="submit" id="form-submit" tabindex="5" value="Upload" required tabindex="1" />
</form>
<script>
    $('#input-form').one('submit',function(){
        var form = document.querySelector('form');
        var request = new XMLHttpRequest();
        var formData = new FormData(form);
        request.open('post', 'transfer.php', true);
        request.onreadystatechange = function() {
            if (request.readyState == 4 && request.status == 200) {
                var return_data = request.responseText;
                var pre = "https://s3-ap-northeast-1.amazonaws.com/icl-destination/";
                var inputq6 = pre.concat(return_data);
                var inputq1 = $('#input-q1').val();
                var inputq2 = $('input[name=week]:checked').val();
                var inputq3 = $('input[name=weekoff]:checked').val();
                if (return_data.length < 3) {
                    if (inputq3 == "YES") {
                        inputq6 = "WeekOff";
                    }
                    else {
                        window.alert("Upload Failed, Please Check Your File and Re-Submit");
                        window.location = 'http://icl.heroku.com';
                        return false;
                    }
                }
                else if (inputq1.length != 9) {
                    window.alert("Please Check Your Studen ID and Re-Submit");
                    window.location = 'http://icl.heroku.com';
                    return false;
                }
                else {
                    var inputq4 = $('input[name=skype-quality]:checked').val();
                    var inputq5 = $('#input-q5').val();
                    var q1ID = "entry.571633517";
                    var q2ID = "entry.1466396105";
                    var q3ID = "entry.616552590";
                    var q4ID = "entry.1185109500";
                    var q5ID = "entry.1248588544";
                    var q6ID = "entry.964286891";
                    var baseURL = 'https://docs.google.com/forms/d/1q9cpMs43a6ZxkMo9W7A1YiUpVf3NHfWN5uc0Voor_eY/formResponse?';
                    var submitRef = '&submit=Upload';
                    var submitURL = (baseURL + q1ID + "=" + inputq1 + "&" + q2ID + "=" + inputq2 + "&" + q3ID + "=" + inputq3 + "&" + q4ID + "=" + inputq4 + "&" + q5ID + "=" + inputq5 + "&" + q6ID + "=" + inputq6 +  submitRef);
                    console.log(submitURL);
                    $.ajax({
                        'async': false,
                        'type': 'POST',
                        'url': submitURL
                    });
                    window.location = 'http://icl-success.heroku.com';
                }
            }
        }
        request.send(formData);
     });
</script>
<brb>
<p id="input-feedback"></p>
<img src="ICL-logo.png" style="width:159.4px;height:69.2px;">
</body>
</html>