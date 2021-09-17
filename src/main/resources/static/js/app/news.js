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
				str += "<a href='" + news[i].originallink + "' id = 'news-li' class='list-group-item d-flex justify-content-between align-items-start list-group-item-action'>";
				str += "<div class='ms-2 me-auto'>";
				str += "<div class='fw-bold mb-1'>" + news[i].title +"</div>";
                str += "<div id='div-desc'>" + news[i].description + "</div>";
                str += "</div>"
                str += "<div id = btn-scrap><button class = 'btn btn-sm btn-outline-secondary'><i class='fas fa-pencil-alt'></i></button></div>"
				str += "</a>";
			}
			$("#ol-news").html(str);
		},
		error : function(e) {
			alert("통신 실패");
		}
	})
})

//str += " data-title = '"+news[i].title+"' ";
//				str += " data-description = '"+news[i].description+"' ";
//				str += " data-originallink = '"+news[i].originallink+"' ";
//				str += " data-pubDate = '"+news[i].pubDate+"' ";
//				str += ">";
//				str += "<td>" + news[i].title + "</td>";
//				str += "<td>" + news[i].description + "</td>";
//				str += "<td>" + news[i].originallink + "</td>";
//				str += "<td>" + news[i].pubDate + "</td>";
//				str += "<td><button class = 'btn btn-default'>scrap</button></td>";