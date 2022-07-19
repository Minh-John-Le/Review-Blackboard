<%@page import = "java.util.List" %>
<%@page import = "java.util.Iterator" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/skeleton/2.0.4/skeleton.css" />
  
  <title>Review Blackboard</title>
  <style>
  .review {
	  margin-left: 20px;
	  padding: 20px;
	}

	.tab 
	{
		display: inline-block;
		margin-left: 40px;
	}
	
	h2 {
  		font-size: 30px;
	}
	
	.heading_right
	{
		display: inline-block;
		margin-left: 500px;
	}
  </style>
  
</head>
<body>


<!-- Review filter-->  
  <div class="container">
 	<span class = "heading">
 		<input type="submit" value="Review Blackboard" name = "Button">
 			
 	</span>
 	<span class = heading_right>
 		Hello, Minh Le 
 		<input type="submit" value="Log Out" name = "Button">
 	</span>
 	
    <h1> Ramin Moazeni</h1>
    <h2> Average Quality: 5</h2>
    <h2>  Average Difficulty: 5</h2>
    <form id="Sign-up-form" action = "homeServlet" method="post">
      <div  align="left">
        <b>Course:</b> <input type="text" name="course" >
      
        <b>From Year:</b> <input type= "text" name= "fromYear">
        
        <b>To Year:</b> <input type = "text" name = "toYear">
         
      </div>   
       
           
<!-- Review filter-->  
      <div align="left">
        <select id = "quality" name = "quality"  >  
          <option> ---Quality--- </option>  
          <option> 1 </option>  
          <option> 2 </option> 
          <option> 3 </option>
          <option> 4 </option>
          <option> 5 </option>      
        </select> 
                
        <select id = "difficulty" name = "difficulty" class = "tab">  
          <option> ---Difficulty--- </option>  
          <option> 1 </option>  
          <option> 2 </option> 
          <option> 3 </option>
          <option> 4 </option>
          <option> 5 </option>      
        </select>
        
        <select id = "classStyle" name = "classStyle" class = "tab">  
          <option> ---Class Style--- </option>  
          <option> In Person </option>
          <option> Online </option>  
          <option> Hybrid </option>      
        </select>   
               
        <select id = "grade" name = "grade" class = "tab">  
          <option> ---Grade--- </option>  
          <option> Not Sure Yet </option>
          <option> A </option>
          <option> B </option>  
          <option> C </option> 
          <option> D </option> 
          <option> E </option> 
          <option> F </option>             
        </select>
                  
      </div>
      
      <span>
         <input type="submit" value="Submit" name = "Button">
      </span>
      <span class = "tab">
       	<input type="submit" value="Write Review" name = "Button">
      </span>
     
      
      
      <hr>
<!-- Review display block-->  

   <div> 
   	<span> <b>Quality: </b> 5   </span>

   	<span class = "tab"> <b>Difficulty: </b> 5 </span>
   	
   	<span class = "tab"> <b>Course: </b> CS157A </span>
   	
   	<span class = "tab"> <b>Grade: </b> Not Sure Yet </span>
   	
   	<span class = "tab"> <b>Year: </b> 2022 </span>
	
   	<span class = "tab"> <b>Quarter/Semester: </b> Summer </span>
   	<br></br>
   	<span> <b>Class Style: </b> Online </span>
   	
   	
   	
  	<blockquote class = "review">
  
  	Lectures are amazing. He always pauses for questions. He goes through the code examples in detail which are helpful. The exercises are well structured and serve as a tutorial to apply the concepts we learned in class. He is very responsive to email and questions and holds extra office hours frequently to help students who are stuck in assignments.You don't know there's a quiz unless you check canvas-- they are posted and due day of. I missed several quizzes because of this and my work schedule. I was never allowed to make it up, being blamed for not checking. He is incredibly unsympathetic towards students who work. Rude in emails.
  	</blockquote>
  	
  	<div align="right">
       	<input type="submit" value="Report" name = "Button">
    </div>
    
  <hr>
  
  </div>
   	</form>
   </div>
</body>
</html>