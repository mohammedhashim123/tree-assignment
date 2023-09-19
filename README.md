"# tree-assignment" 
After clone the project from Github and run the command of clean install in maven
  #1 API is Authentication API [Post] which accept user name and password and return jwt token to access statement API,Below is an exmpale of request and response 
	-Url - > http://localhost:8050/authenticate
	-Request Param - > {"username":"user","password":"user"}
	-Response Param - > 
	{
		"token": "eyJ0eXBlIjoiYWNjZXNzX3Rva2VuIiwiYWxnIjoiSFM1MTIifQ.eyJzdWIiOiJ1c2VyIiwicm9sZSI6W3siYXV0aG9yaXR5IjoidXNlciJ9XSwiZXhwIjoxNjk1MDY3NjI1LCJpYXQiOjE2OTUwNjczMjV9.yCbwM88RUIwcWqZon-Ax6nFv1EBs7VHDgb8roxSiHjUrCPdNuYCpZsq6U_4dHZURCG83H4tRWQMtKGU_zYp-vQ",
		"role": "user" 
	}
	
  #2 API is statement report API [Get] which accept acountId , from - to date and from - to amount as search parameters and use the jwt token from above API to contorl access
	-Expmple of how to call : 
	URL#1 - > http://localhost:8050/statement/getStatementByAccount?accountId=3
	URL#2 - > http://localhost:8050/statement/getStatementByAccount?accountId=3&fromDate=2020-08-01&toDate=2021-08-01
	URL#3 - > http://localhost:8050/statement/getStatementByAccount?accountId=3&fromAmount=1&toAmount=2000
  