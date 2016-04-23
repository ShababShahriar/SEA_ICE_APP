<!DOCTYPE html>
<html>
<head>
<style>
table {
    width: 100%;
    border-collapse: collapse;
}

table, td, th {
    border: 1px solid black;
    padding: 5px;
}

th {text-align: left;}
</style>
<script>
    var base_url = <?php echo base_url(); ?>
    var xmlhttp=new XMLHttpRequest();
        xmlhttp.onreadystatechange=function(){
            if (xmlhttp.readyState==4 && xmlhttp.status==200){
                   alert(xmlhttp.responseText);
                   console.log(xmlhttp.responseText);// you will see OKKK in console
             }
        }
    xmlhttp.open("GET",base_url+"/admin/process3",true); // first try `../index.php/example` ( extension depends if you enable/disable url rewrite in apache.conf ) , if this won't work then try base_url/index.php/example ( where you can specify base_url by static or with CodeIgniter helpher function )
    xmlhttp.send();
</script>
</head>
<body>


</body>
</html>