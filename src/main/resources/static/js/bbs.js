function shan(){
	var arr = document.getElementsByTagName("li");
	var numm = parseInt(prompt("请确定删除几号帖子：",""));
		arr[numm-1].className="nnn"
		console.log(arr[numm-1]);
}
function fa() {
    document.getElementById("post1").style.display = "block";
}
function touxiang() {
    var tou = new Array("tou01.jpg", "tou02.jpg", "tou03.jpg", "tou04.jpg");
    var sui = parseInt(Math.random() * 4);
    return tou[sui];
}
function chu() {
    document.getElementById("post1").style.display = "none";
    var s = document.getElementsByTagName("ul")[0];
    var a = document.createElement("li");
    var b = document.createElement("div");
    var c = document.createElement("img");
    var d = document.createElement("h1");
    var e = document.createElement("p");
    var f = document.createElement("span");
    s.appendChild(a);
    a.className = ".bbs section ul li";
    a.appendChild(b);
    b.className = ".bbs section ul li div";
    b.appendChild(c);
    c.className = ".bbs section ul li div img";
    c.src = "img/" + touxiang();
    a.appendChild(d);
    d.className = ".bbs section ul li h1";
    d.innerHTML = document.getElementById("title").value;
    a.appendChild(e);
    e.className = ".bbs section ul li p";
    e.innerHTML = "版块：" + document.getElementsByTagName("select")[0].value;
    e.appendChild(f);
    f.className = ".bbs section ul li p span";

	
    var today = new Date();
    var nian = today.getFullYear();
    var yue = today.getMonth() + 1;
    var ri = today.getDate();
    var shi = today.getHours();
    var fen = today.getMinutes();
    var miao = today.getSeconds();
    f.innerHTML = "发表时间：" + nian + "-" + yue + "-" + ri + "&nbsp;" + shi + ":" + fen + ":" + miao;
    document.getElementById("title").value = "";
    document.getElementsByTagName("select")[0].value = "";
    s.insertBefore(a, s.firstElementChild);
}
function addElement() {
    //创建TextNode节点
    var person = document.createTextNode(form1.person.value);
    var content = document.createTextNode(form1.content.value); 
    //创建td类型的Element节点
    var td_person = document.createElement("td"); 
    var td_content = document.createElement("td");  
    var tr = document.createElement("tr"); //创建一个tr类型的Element节点
    var tbody = document.createElement("tbody"); //创建一个tbody类型的Element节点
    //将TextNode节点加入到td类型的节点中
    td_person.appendChild(person);
    td_content.appendChild(content);    
    //将td类型的节点添加到tr节点中
    tr.appendChild(td_person);
    tr.appendChild(td_content); 
    tbody.appendChild(tr); //将tr节点加入tbody中
    var tComment = document.getElementById("comment"); //获取table对象
    tComment.appendChild(tbody); //将节点tbody加入节点尾部
    form1.person.value="";  //清空评论人文本框
    form1.content.value="";     //清空评论内容文本框
}
//删除第一条评论
function deleteFirstE(){
    var tComment = document.getElementById("comment"); //获取table对象
    if(tComment.rows.length>1){
        tComment.deleteRow(1);      //删除表格的第二行，即第一条评论，
    }
}
//删除最后一条评论
function deleteLastE(){
    var tComment = document.getElementById("comment"); //获取table对象
    if(tComment.rows.length>1){
        tComment.deleteRow(tComment.rows.length-1); //删除表格的最后一行，即最后一条评论
    }
}
function view(){
	var tabll = document.getElementById("tabll");
	tabll.style.display = "block";
}

