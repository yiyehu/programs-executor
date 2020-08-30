package tech.yiyehu.executor.modules.app.executor;

import tech.yiyehu.executor.common.utils.ResultException;
import com.alibaba.fastjson.JSON;
import org.python.core.*;
import org.python.util.PythonInterpreter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
@ConfigurationProperties(prefix = "exe.python")
public class PythonProgramExecutor {

    String exe;

    public void setExe(String exe) {
        this.exe = exe;
    }

    /**
     * 直接执行命令
     * @param commands
     * @return python 文件执行后向控制台打印的字符串
     * @throws IOException
     */
    public List exe(String[] commands) throws IOException {
        List<String> list = new ArrayList();
        Process process = null;
        if(exe != null){
            commands[0] = exe;
        }
        try {
            process = Runtime.getRuntime().exec(commands);
        } catch (Exception e) {
            System.out.println("Exception Raised" + e.toString());
        }
        InputStream stdout = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stdout, StandardCharsets.UTF_8));
        String line;
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        return list;
    }

    /**
     * 执行简单的python程序中的方法，但是因为Jython执行时找不到包的问题，只能执行简单的 TODO
     *
     * @param file
     * @param fun
     * @param cmds
     * @return
     */
    public String exeFunction(String file, String fun, PyString[] cmds) {
        PythonInterpreter interpreter = new PythonInterpreter();
        PySystemState sys = Py.getSystemState();
        System.out.println(sys.getPlatform());
        sys.path.add("C:\\Anaconda3\\envs\\zy\\Lib");
        interpreter.execfile(file);
        PyFunction function = (PyFunction) interpreter.get(fun, PyFunction.class);
        PyObject pyobject = null;
        if (cmds == null || cmds.length == 0) {
            pyobject = function.__call__();
        } else {
            pyobject = function.__call__(cmds);
        }
        System.out.println("anwser = " + pyobject.toString());
        return pyobject.asString();
    }

    /**
     *
     *
     *
     */
    /**
     * @param structures 定义一个返回值类型的数据结构，用','分隔structures，每一个struct描述一个类型（数据结构）,其类型描述如下param为参数名称。
     *                   param:string
     *                   param:array:length:type ,
     *                   if length == 0,length undefined,type could be string or array(the type of array must be string) or map
     *                   param:map -> json string to map
     *
     * @param list       Python文件执行结果
     * @return
     */
    public Map dealResult(String structures, List<String> list) throws Exception {
        String[] structArray = structures.split(",");
        int index = 0;
        Map<String, Object> map = new HashMap();
        for (int i = 0; i < structArray.length; i++) {
            if (index >= list.size()) break;
            String[] typeStruct = structArray[i].split(":");
            switch (typeStruct[1]) {
                case "string":
                    map.put(typeStruct[0], list.get(index));
                    index++;
                    break;
                case "array":
                    List<String> temp = list.subList(index, ("0".equals(typeStruct[2]) ? list.size() : index + Integer.parseInt(typeStruct[2])));
                    if ("string".equals(typeStruct[3])) {
                        map.put(typeStruct[0], temp);
                    } else if ("array".equals(typeStruct[3])) {
                        List<List<String>> arrayElem = new ArrayList<>();
                        temp.stream().forEach(ele -> {
                            arrayElem.add(Arrays.asList(ele.split(",")));
                        });
                        map.put(typeStruct[0], arrayElem);
                    } else if ("map".equals(typeStruct[3])) {
                        List<Object> arrayElem = new ArrayList<>();
                        temp.stream().forEach(ele -> {
                            arrayElem.add(JSON.parse(ele));
                        });
                        map.put(typeStruct[0], arrayElem);
                    }
                    if ("0".equals(typeStruct[2])) {
                        index = list.size();
                    } else {
                        index += Integer.parseInt(typeStruct[2]);
                    }
                    break;
                case "map":
                    map.put(typeStruct[0], JSON.parse(list.get(index)));
                    index++;
                    break;
                default:
                    throw new ResultException("无此结构");
            }
        }
        return map;
    }
}
