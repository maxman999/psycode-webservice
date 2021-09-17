$(document).ready(function(){
	let keyObj = {
			newsKeyword : "인공지능"
		};
	$.ajax({
		type : 'get',
		url : '/getNews',
		contentType : "application/json; charset=utf-8",
		data : keyObj,
		success : function(result) {
			let news = JSON.parse(result).items;
			let str = "";
			for (var i = 0; i < news.length; i++) {
				str += "<tr id = 'tr-scrap' class='odd gradeX' ";
				str += " data-title = '"+news[i].title+"' ";
				str += " data-description = '"+news[i].description+"' ";
				str += " data-originallink = '"+news[i].originallink+"' ";
				str += " data-pubDate = '"+news[i].pubDate+"' ";
				str += ">";
				str += "<td>" + news[i].title + "</td>";
				str += "<td>" + news[i].description + "</td>";
				str += "<td>" + news[i].originallink + "</td>";
				str += "<td>" + news[i].pubDate + "</td>";
				str += "<td><button class = 'btn btn-default'>scrap</button></td>";
				str += "</tr>";
			}
			$(".news-table").html(str);
		},
		error : function(e) {
			alert("통신 실패");
		}
	})
})