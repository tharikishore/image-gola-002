<% include '/WEB-INF/includes/header.gtpl' %>
 
<div class="col-md-4">
    <h3>Filter</h3>
    <form method="post" action="/list" class="form-horizontal">
    <p>
    	Source
		<select name="imgSrc">
		  <option value="All">All</option>
		  <option value="Google">Google</option>
		  <option value="Yahoo">Yahoo</option>
		</select> 
	</p>
    <p>
    	Aspect Ratio
		<select name="aspectRatio">
		  <option value="All">All</option>
		  <option value="Portrait">Portrait</option>
		  <option value="Landscape">Landscape</option>
		</select> 
	</p>
		<input type="submit" value="filter">
    </form>

</div>

<table border="1">
  <thead>
     <tr>
        <th>Source</th>
        <th>Search Keyword</th>
        <th>Image Url</th>
        <th>Size</th>
        <th>Height</th>
        <th>Width</th>
        <th>Aspect Ratio</th>
     </tr>
  </thead>
  <tbody>
     <% request.imgs.each { img -> %>
        <tr>
           <td>${img.imgSrc}</td>
           <td>${img.searchKey}</td>
           <td>
           	<img src="${img.imgUrl}" height="32" width="32"></img>
           </td>
           <td>${img.imgSize}</td>
           <td>${img.height}</td>
           <td>${img.width}</td>
           <td>${img.aspectRatio}</td>
           
        </tr>
     <% } %>
  </tbody>
</table>
 
<% include '/WEB-INF/includes/footer.gtpl' %>
