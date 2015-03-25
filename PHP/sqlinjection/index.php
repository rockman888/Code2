
<?php

	function insertQuery($conn)
	{
		if (isset($_POST['insertProductId']) )
		{
			$strsql = "INSERT INTO CUSTOMERS VALUES(?, ?)";
			if ($stmt = $conn->prepare($strsql))
			{
				$pw = "12345";
				// bind the variables to the paramter as string
				$stmt->bind_param("ss", $_POST['insertProductId'], $pw);	// hai tham số
				
				// execute the statement
				$stmt->execute();
				
				// Close the prepared statement
				$stmt->close();
				echo "Insert Succeed!<br>";
			}
		}
	}
	
	
	function updateQuery($conn, $id=1)
	{
		if (isset($_POST['updateProductName']))
		{
			$strsql = "UPDATE PRODUCT SET NAME = ? WHERE id = ?";

			if ($stmt = $conn->prepare($strsql))
			{
				$stmt->bind_param("ss", $_POST['updateProductName'], $id);
				$stmt->execute();		
				$stmt->close();
				echo "Update succeed!<br>";
			}			
		}
	}
	
	
	function deleteQuery($conn)
	{
		if (isset($_POST['deleteProductId']))
		{
			$strsql = "DELETE FROM PRODUCT WHERE id = ?";
			if ($stmt = $conn->prepare($strsql))
			{
				// bind
				$stmt->bind_param("s", $_POST['deleteProductId']);				
				$stmt->execute();				
				$stmt->close();
				echo "Delete succeed!<br>";
			}			
		}
	}

	if (isset($_POST['ok']))	
	{
		$conn = mysqli_connect ('localhost', 'root', '', 'info') or die("cannot connect!");
		
		if (isset($_POST['name']))
		{
			$strsql = "SELECT * FROM customers WHERE USER = '" . $_POST['name'] . "'";
			
			echo $strsql;
			$result = $conn->query($strsql) or die ("\n--> sql error");
			
			while($row = $result->fetch_assoc())
			{
				echo "<p> " . $row['user'] . " - " . $row['password'] . "</p>";
			}	
		}
		
		echo "<br>";
		
		if (isset($_POST['productid']))
		{
			// sql injecttion
			if ($stmt = $conn->prepare("SELECT id, name, date FROM product WHERE ID = ?"))
			{
				$id = $_POST['productid'];
				
				// bind a variable to the parameter as a string
				$stmt->bind_param("s", $id );
				
				// Execute the statement
				$stmt->execute();
				
				// Get the variables from the query
				$stmt->bind_result($id, $name, $date);
				
				$stmt->fetch();
				
				// display the data
				printf("id: %s - name: %s - date = %s\n", $id, $name, $date);
				
				// close the prepared statement
				$stmt->close();
			}
		}
		
		// insertQuery($conn);
		// updateQuery($conn, 3);
		deleteQuery($conn);
    }	
?>

<form action="<?php echo "index.php" ?>" method="post">    

	<table>
    	<tr>
        	<td>User: </td>
            <td><input type="text" name="name" /> </td>
        </tr>
        
    	<tr>
        	<td>ProductID: </td>
            <td><input type="text" name="productid" /> </td>
        </tr>
        
        <tr>
        	<td>Insert user: </td>
            <td><input type="text" name="insertProductId" /> </td>
        </tr>
        
                
    	<tr>
        	<td>Update Product Name: </td>
            <td><input type="text" name="updateProductName" /> </td>
        </tr>

    	<tr>
        	<td>Delete ProductID: </td>
            <td><input type="text" name="deleteProductId" /> </td>
        </tr>
        
		<tr>
        	<td colspan="2"> <input type="submit" name="ok" value="search"> </td>
        </tr>
    </table>
</form>

