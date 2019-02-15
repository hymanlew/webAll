
var basePath = null;

function getPath() {
    //获取当前网址，如： http://localhost:8080/ems/Pages/Basic/Person.jsp
    var currentUrl = location.href;

    //获取主机地址之后的目录，如： /ems/Pages/Basic/Person.jsp
    var pathName = location.pathname;
    var word = currentUrl.indexOf(pathName);

    //获取主机地址，如： http://localhost:8080
    var localhostPath = currentUrl.substring(0,word);

    //获取带"/"的项目名，如：/ems
    var projectName = pathName.substring(0,pathName.substring(1).indexOf("/")+1);

    basePath = localhostPath + projectName + '/';
    return basePath;
}