package io.hiwepy.cloud.authz.dbperms.setup.aspect;

import org.aspectj.lang.ProceedingJoinPoint;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据权限切面：对增加，修改，删除用户；角色分配用户操作进行拦截进行相应的数据权限操作
 *
 * @author <a href="https://github.com/hiwepy">wandl</a>
 * @since 2019-12-03
 */
public class DataPermissionAspectInterceptor {

    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        String mathodName = joinPoint.getSignature().getName();
        if ("setSelf".equalsIgnoreCase(mathodName)) {
            return result;
        }
        Object[] args = joinPoint.getArgs();
        Map<String, Object> params = new HashMap<String, Object>();
		/*
 		if("insert".equalsIgnoreCase(mathodName)){
			
			SecurityPrincipal principal = SubjectUtils.getPrincipal(SecurityPrincipal.class);
			
			YhglModel model = (YhglModel) args[0];
			List<YhJssjfwModel> yhjssjfwList = new ArrayList<YhJssjfwModel>();
			YhJssjfwModel sjfw = null;
			if(!BlankUtils.isBlank(model.getCbvjsxx())){
				//循环新提交的角色
				for (String jsdm : model.getCbvjsxx()) {
					// 用户数据范围对象
					//保存用户,角色数据范围
					//如果机构代码为-1则为超级管理员
					//如果机构类别为1则为教学院系，其它为全校。
					sjfw = new YhJssjfwModel();
					
					if(!"-1".equals(model.getJg_id())){
						if(!"1".equals(model.getJglb())){
							sjfw.setSjfwztj("jg_id=-3");//代码所有学院数据权限
						}else{
							sjfw.setSjfwztj("jg_id="+model.getJg_id());
						}
					}else{
						sjfw.setSjfwztj("jg_id=-1");
					}
					sjfw.setSjfwzmc(model.getJgmc());
					sjfw.setYhm(model.getYhm());
					sjfw.setJs_id(jsdm);
					sjfw.setKzdx("xs");
					yhjssjfwList.add(sjfw);
				}
			}
			
			params.put("yhjssjfwList", yhjssjfwList);
			getYhsjfwDao().zjYhsjfwxx(params);
		}else if("update".equalsIgnoreCase(mathodName)){
			YhglModel model = (YhglModel)args[0];
			List<String> addList = (List<String>) model.getQueryList();
			//有新角色
			if(!BlankUtils.isBlank(addList)){
				List<YhJssjfwModel> yhjssjfwList = new ArrayList<YhJssjfwModel>();
				YhJssjfwModel sjfw = null;
				for (String jsdm : addList) {
					//保存用户,角色数据范围
					sjfw = new YhJssjfwModel();
					sjfw.setSjfwz_id(getQueryDao().getSysGuid());
					sjfw.setSjfwzmc(model.getJgmc()+"【学生数据】");
					sjfw.setYhm(model.getYhm());
					sjfw.setJs_id(jsdm);
					sjfw.setKzdx("xs");
					sjfw.setSjfwztj("jg_id="+model.getJg_id());
					yhjssjfwList.add(sjfw);
					sjfw = new YhJssjfwModel();
					sjfw.setSjfwz_id(getQueryDao().getSysGuid());
					sjfw.setSjfwzmc(model.getJgmc()+"【课程数据】");
					sjfw.setYhm(model.getYhm());
					sjfw.setJs_id(jsdm);
					sjfw.setKzdx("kc");
					sjfw.setSjfwztj("jg_id="+model.getJg_id());
					yhjssjfwList.add(sjfw);
				}
				
				params.put("yhm", model.getYhm());
				params.put("deleteList", model.getDeleteList());
				params.put("yhjssjfwList", yhjssjfwList);
				getYhsjfwDao().xgYhsjfwxx(params);
			}
		}else if("delete".equalsIgnoreCase(mathodName)){
			YhglModel model = (YhglModel)args[0];
			params.put("deleteList", model.getDeleteList());
			getYhsjfwDao().scYhsjfwxx(params);
		}else if("doAllot".equalsIgnoreCase(mathodName)){
			
			JsglModel model = (JsglModel)args[0];
			
			// 有数据增加
			if (!BlankUtils.isBlank(model.getYhm_list())) {
				
				// 循环新分配的用户名
				YhJssjfwModel sjfw = null;
				List<YhJssjfwModel> jsyhsjfwList = new ArrayList<YhJssjfwModel>();
				for (String yhm : model.getYhm_list()) {
					// 保存用户、角色数据范围
					YhglModel yhgl = new YhglModel();
					
					yhgl.setYhm(yhm);
					
					yhgl = getYhglDao().getModel(yhgl);

					// 机构不为空且不是是给学生角色分配学生
					if (!BlankUtils.isBlank(yhgl.getJg_id()) && !"xs".equals(model.getJsdm())) {

						// 保存用户,角色数据范围
						sjfw = new YhJssjfwModel();
						sjfw.setSjfwz_id(getQueryDao().getSysGuid());
						sjfw.setSjfwzmc(model.getJgmc() + "【学生数据】");
						sjfw.setYhm(yhm);
						sjfw.setJs_id(model.getJsdm());
						sjfw.setKzdx("xs");
						sjfw.setSjfwztj("jg_id=" + model.getJg_id());
						jsyhsjfwList.add(sjfw);

						sjfw = new YhJssjfwModel();
						sjfw.setSjfwz_id(getQueryDao().getSysGuid());
						sjfw.setSjfwzmc(model.getJgmc() + "【课程数据】");
						sjfw.setYhm(yhm);
						sjfw.setJs_id(model.getJsdm());
						sjfw.setKzdx("kc");
						sjfw.setSjfwztj("jg_id=" + model.getJg_id());
						jsyhsjfwList.add(sjfw);

					}

				}
				params.put("jsdm",model.getJsdm());
				params.put("jsyhsjfwList",jsyhsjfwList  );
				getYhsjfwDao().zjJsyhfpSjfwxx(params);
			}
		}else if("doUnAllot".equalsIgnoreCase(mathodName)){
			JsglModel model = (JsglModel)args[0];
			// 有数据删除
			if (!BlankUtils.isBlank(model.getYhm_list())) {
				params.put("yhm_list",model.getYhm_list());
				params.put("jsdm",model.getJsdm());
				getYhsjfwDao().scJsyhfpSjfwxx(params);
			}
		}*/
        return result;
    }

}
