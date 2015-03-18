
<!-- 
	database
		- question:
			id (primary)
			title
			date		
		- answer:
			id (primary)
			title
			account	// so luong nguoi chon 
			questionID (Foregin)
			
	questionID (answer) -> id (question)	
	
	Result on web
	Question: do you like coding?
		don't like : 2 (3.7 %)
		like : 30 (65.11 %)
		really like : 16 (31.19 %)
	
	Thanks for choose!	
-->

<?php 
	Include_once ("connect.php");
	
	if (isset($_POST['ok']))
	{
		$id = $_POST['answer'];
		$qid = $_GET['questionid'];
		
		$sql3 = "update ANSWER set account=account+1 where id='" . $id . "'";		
		$result = $conn->query($sql3);
		
		header("location: result.php?questionid=$qid");
		
		// header ("location: poll.php");
		exit();		
	}
	
	/*lua chon cau hoi liet ke ra*/
	$sql = "select * from question order by id desc";
	$result = $conn->query($sql);
	
	while ($row = $result->fetch_assoc())	// duyet het trong mang array
	{
		$qid = $row['id'];
		echo "<form action='poll.php?questionid=$qid' method='post'>";
		echo "<h2>$row[title]</h2>";
		
		$sql2 = "select * from answer where questionID='$qid' order by id";
		$query2 = $conn->query($sql2);
			
		$row2 = $query2->fetch_assoc();
		while ($row2 = $query2->fetch_assoc())
		{			
			echo "<input type=radio name=answer value=$row2[id]> $row2[title] <br/>";
		}
		
		echo "<input type='submit' name='ok' value='Binh Chon'>";
		echo "<a href='result.php?questionid=$qid'> View </a>";
		echo "</form>";				
	}	
?>