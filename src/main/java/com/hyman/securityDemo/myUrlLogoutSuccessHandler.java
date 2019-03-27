package com.hyman.securityDemo;

//@Component
//public class myUrlLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
//
//    private final Log log = LogFactory.getLog(this.getClass());
//    @Override
//    public void onLogoutSuccess(HttpServletRequest request,
//                                HttpServletResponse response,
//                                Authentication authentication
//    ) throws IOException, ServletException {
//        User user = (User) ((authentication == null)?null:authentication.getPrincipal());
//        HttpSession session = request.getSession();
//        String session_id = (session == null)?"":session.getId();
//        if(user == null){
//            log.warn("logout unknown user, session："+session_id);
//        }else{
//            log.debug("logout user:{}, id:{}, session："+new Object[]{user.getName(),user.getId(),session_id});
//        }
//        response.sendRedirect(request.getContextPath() + "/security/login");
//        return;
//    }
//}
