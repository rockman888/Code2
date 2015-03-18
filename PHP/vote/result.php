<?php

	
	/*
	---------------------
	$sql1
	id 	title 	date
	0 	do you like coding? 	2015-12-20		
	
	---------------------	
	$sql2
	id 	total 	
	0 	48		
	
	---------------------	
	$sql3
	title 	account questionID
	0 	don't like 		 2	0
	1 	like 			30	0
	2 	Really like 	16	0
	
	*/
	
	
	if (isset($_GET['questionid']))
	{	
		Include_once("connect.php");	

		$qid = $_GET['questionid'];	
		$sql1 = "select * from question where id = " . $qid;
		$result1 = $conn->query($sql1);
		$row1 = $result1->fetch_assoc();
		echo "<h2> $row1[title] </h2>";	/*in title*/
				
		$sql2 = "select id, SUM(account) as total from ANSWER GROUP BY questionid HAVING questionID = " . $qid;		
		$result2 = $conn->query($sql2);
		$row2 = $result2->fetch_assoc();
		$total = $row2['total'];		// cot 'total'		
				
		$sql3 = "Select * from ANSWER where questionID = " . $qid . " order by id";
		$result3 = $conn->query($sql3);
		
		while ($row3 = $result3->fetch_assoc())
		{
			$percent = round ( ($row3['account'] / $total) * 100, 2);			
			echo "<h4 style='color:red'> $row3[title] : $row3[account] ($percent %) </h4>";			
		}
		
		echo "Thanks for choose!";
	}
	else
	{
		echo "ko tim hay questionID";
	}
?>