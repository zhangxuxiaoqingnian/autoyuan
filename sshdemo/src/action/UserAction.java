package action;

import bean.UserEntity;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import service.UserImpl;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserAction extends ActionSupport {

    private String mobile = "";
    private String password = "";

    private File file; //上传的文件
    private String fileName; //文件名称

    private String savePath;

    private UserImpl user;

    private Map<String, Object> jsonData;

    public void setUser(UserImpl user) {
        this.user = user;
    }

    public UserImpl getUser() {
        return user;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Map<String, Object> getJsonData() {
        return jsonData;
    }

    public void setJsonData(Map<String, Object> jsonData) {
        this.jsonData = jsonData;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public String getSavePath() {
        return savePath;
    }


    /**
     * 注册
     *
     * @return
     */
    public String register() {
        jsonData = new HashMap<>();

        if (!mobile.equals("")&&!password.equals("")){
            if (user.isExist(mobile)) {

                jsonData.put("msg", "用户已注册");
                jsonData.put("code", "1");
                jsonData.put("data", "{}");

            } else {

                UserEntity userEntity = new UserEntity();
                userEntity.setMobile(mobile);
                userEntity.setPassword(password);
                userEntity.setUsername(mobile);
                user.add(userEntity);

                jsonData.put("msg", "注册成功");
                jsonData.put("code", "0");
                jsonData.put("data", userEntity);
            }
        }else{
            jsonData.put("msg", "用户名或密码不能为空");
            jsonData.put("code", "1");
        }



        return SUCCESS;


    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public String getUserInfo() {
        jsonData = new HashMap<>();

        if (!mobile.equals("")){

            UserEntity userEntity = (UserEntity) getUser().getSf().openSession().createQuery("from UserEntity where mobile = '" + mobile + "'").list().get(0);

            System.out.println("session:"+ActionContext.getContext().getSession());
            if (userEntity != null) {

                jsonData.put("code", "0");
                jsonData.put("msg", "获取用户信息成功");
                jsonData.put("data", userEntity);

            }
        }else{
            jsonData.put("code", "1");
            jsonData.put("msg", "用户名不能为空");
        }


        return SUCCESS;


    }

    /**
     * 登录
     * @return
     */
    public String login() {
        jsonData = new HashMap<>();


        if (!mobile.equals("")&&!password.equals("")){
            //当前对象是否有记录
            List<UserEntity> list = getUser().getSf().openSession().createQuery("from UserEntity where mobile = '"+mobile+"'").list();

            if (list!=null&&list.size()>0){
                UserEntity user = list.get(0);
                if (mobile.equals(user.getMobile())&&user.getPassword().equals(password)){
                    jsonData.put("code","0");
                    jsonData.put("msg","登录成功");
                    jsonData.put("data",user);

                }else{
                    jsonData.put("code","1");
                    jsonData.put("msg","用户名或密码错误");
                }
            }else{
                jsonData.put("code","1");
                jsonData.put("msg","用户不存在");
            }
        }else{
            if (mobile.equals("")){
                jsonData.put("msg","用户名或密码不能为空");
            }

            jsonData.put("code","1");

        }



        return SUCCESS;
    }


    public String add() {


        return SUCCESS;
    }


    /**
     * 上传图片
     *
     * @return
     */
    public String upload() {
        jsonData = new HashMap<>();

        String filename = System.currentTimeMillis()+"";

        String realpath = ServletActionContext.getServletContext().getRealPath("/images");
        if (file != null) {
            try {
                String path = realpath + "/" + filename;
                FileUtils.copyFile(file, new File(path));
                ActionContext.getContext().put("message", "文件上传成功");
                UserEntity userEntity = (UserEntity) getUser().getSf().openSession().createQuery("from UserEntity where mobile = '" + mobile + "'").list().get(0);
                InetAddress addr = InetAddress.getLocalHost();
                String ip = addr.getHostAddress().toString();
                System.out.println("ipip===" + ip);
                userEntity.setMobile(mobile);
                userEntity.setIcon("http://" + ip + ":8888/images/" + filename);
                user.update(userEntity);
                jsonData.put("code", "0");
                jsonData.put("msg", "文件上传成功");
                return SUCCESS;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else{
            jsonData.put("code", "1");
            jsonData.put("msg", "文件不能为空");
        }
        /**
         * 若要存入数据库
         * fileName是在entity实体类中声明存放文件名称的变量
         * yu.setFileName(imageFileName) 这样将文件名称存入数据库
         * 文件路径为：savefile
         */
        return SUCCESS;

    }

}
