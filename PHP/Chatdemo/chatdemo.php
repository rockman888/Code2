<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Untitled Document</title>

	<link href="yui.css" media="all" rel="stylesheet" />

</head>

<style>	
	.title{
		color: red;
		font-weigh:bold;
		font-style:italic;
		text-transform:lowercase;
	}
	
	td
	{
		margin: 10px;
		padding: 2px;
		text-align::center;
	}

</style>

<body>	
    <?php
		/*
			chatinfo
			- nick (varchar[30] - primary), 
			- message (varchar[160]), 
			- timechat (timestamp)
		*/
	
		include_once ('connectdb.php');		
	
		if (isset($_POST['msg']))	// tồn tại thì mới tiếp tục
		{					
			$sql  = "Insert Into chatinfo SET message='" . $_POST['msg'] . "'";
			$result = $conn->query($sql);
		}
	
	?>
    
    
	<form method="POST">
		<table>
        	<tr>
            	<td class='title'> Email </td>         
                <td> <input type="password" name="pw"/>       
            </tr>
            <tr>
            	<td class='title'> Message </td>
                <td> <textarea name="msg" width="400px" height="300px"> </textarea> </td>
            </tr>
            <tr>
            	<td colspan="2"> <input type="submit" value="send" /> </td>
            </tr>
        </table>
        	
	</form>
    
    <?php
		$sql = "Select message from chatinfo order by timechat desc";		
		$result = $conn->query($sql);
		
		while ($row = $result->fetch_assoc())		
		{
			echo '<p>' . $row['message'] . '</p>';
		}
	?>

</body>

</html>