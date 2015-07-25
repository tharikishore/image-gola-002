<% include '/WEB-INF/includes/header.gtpl' %>
 
<%
  def employee = request.getAttribute("Employee")
  boolean existingKey = employee?.key
  String action = !existingKey ? 'Add' : 'Update'
%>
 
<h2>${action} Employee</h2>
 
<form action="/employee/save" method="POST">
   <table border="0">
      <tbody>
         <tr>
            <td>Name:</td>
            <td><input type="text" name="name" value="${employee?.name ? employee.name : ''}"></td>
         </tr>
         <tr>
            <td>Designation:</td>
            <td><input type="text" name="designation" value="${employee?.designation ? employee.designation : ''}"></td>
         </tr>
        
       
        
      </tbody>
      <% if(existingKey) { %>
         <input type="hidden" name="id" value="${employee.key.id}">
      <% } %>
   </table>
   <br>
   <input type="submit" value="${action}">
   <input type="button" value="Cancel" onclick="javascript:document.location.href = '/employees';">
</form>
 
<% include '/WEB-INF/includes/footer.gtpl' %>
