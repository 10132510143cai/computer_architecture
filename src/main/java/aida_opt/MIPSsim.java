package aida_opt;

import java.io.*;
import java.util.*;

/**
 * On my honor, I have neither given nor received unauthorized aid on this assignment.
 * 51174500002 蔡璟辉
 */
public class MIPSsim
{
    /**
     * 判断是否为windows操作系统
     * @return
     */
    public static boolean is_windows(){
        Properties props = System.getProperties();
        String operation_system = props.getProperty("os.name");
        operation_system = operation_system.toLowerCase();
        int index = operation_system.indexOf("windows");
        return (index>=0);
    }

    /**
     * 获取输入文件
     * @param path 文件路径
     * @return 指令列表
     * @throws Exception
     */
    public static List<String> getInputCommand(String path)throws Exception{
        File file = new File(path);
        List<String> inputcommand = new LinkedList<String>();
        BufferedReader reader = null;

        String tempString = "";
        reader = new BufferedReader(new FileReader(file));
        while ((tempString = reader.readLine()) != null) {
            inputcommand.add(tempString);
        }
        reader.close();
        return inputcommand;
    }

    /**
     * 求二进制转十进制
     * @param binary 二进制String串
     * @return 十进制String串
     */
    public static String binaryToDec(String binary){
        boolean isnegative = false;
        if(binary.startsWith("1")){
            isnegative = true;
            char[] c = binary.toCharArray();
            for (int i=0; i<binary.length(); i++) {
                if (Character.getNumericValue(c[i]) == 0) {
                    c[i] = '1';
                } else {
                    c[i] = '0';
                }
            }

            boolean finded = false;
            for(int i=binary.length()-1; i>=0 && !finded; i--){
                if(Character.getNumericValue(c[i])==0){
                    c[i] = '1';
                    finded = true;
                }else{
                    c[i] = '0';
                }
            }
            binary=String.valueOf(c);
        }
        String dec = Integer.toString(Integer.parseInt(binary,2));
        if(isnegative){
            dec = "-"+dec;
        }
        return dec;
    }

    /**
     * 解析指令形式
     * @param isCategory1 是否是第一类指令
     * @param identification_bits 指令码
     * @return
     */
    public static String getInstruction(boolean isCategory1, String identification_bits){
        if(isCategory1){
            if(identification_bits.equals("0000")){
                return "J";
            }else if(identification_bits.equals("0001")){
                return "JR";
            }else if(identification_bits.equals("0010")){
                return "BEQ";
            }else if(identification_bits.equals("0011")){
                return "BLTZ";
            }else if(identification_bits.equals("0100")){
                return "BGTZ";
            }else if(identification_bits.equals("0101")){
                return "BREAK";
            }else if(identification_bits.equals("0110")){
                return "SW";
            }else if(identification_bits.equals("0111")){
                return "LW";
            }else if(identification_bits.equals("1000")){
                return "SLL";
            }else if(identification_bits.equals("1001")){
                return "SRL";
            }else if(identification_bits.equals("1010")){
                return "SRA";
            }else if(identification_bits.equals("1011")){
                return "NOP";
            }else{
                return "ERROR";
            }
        }else{
            if(identification_bits.equals("0000")){
                return "ADD";
            }else if(identification_bits.equals("0001")){
                return "SUB";
            }else if(identification_bits.equals("0010")){
                return "MUL";
            }else if(identification_bits.equals("0011")){
                return "AND";
            }else if(identification_bits.equals("0100")){
                return "OR";
            }else if(identification_bits.equals("0101")){
                return "XOR";
            }else if(identification_bits.equals("0110")){
                return "NOR";
            }else if(identification_bits.equals("0111")){
                return "SLT";
            }else if(identification_bits.equals("1000")){
                return "ADDI";
            }else if(identification_bits.equals("1001")){
                return "ANDI";
            }else if(identification_bits.equals("1010")){
                return "ORI";
            }else if(identification_bits.equals("1011")){
                return "XORI";
            }else{
                return "ERROR";
            }
        }
    }

    /**
     *
     * @param address 当前指令地址
     * @param register 寄存器目前的值
     * @param instuctionmap 指令Hashmap
     * @return
     */
    public static int execute_instrucion(int address, int[] register,
                                  Map<String,String> instuctionmap){
        String instruct = instuctionmap.get(Integer.toString(address));
        String opocode = instruct.substring(0,instruct.indexOf("\t"));
        instruct = instruct.substring(instruct.indexOf("\t"));
        if(opocode.equals("ADD")){
            int rd = instruct.indexOf("R")+1;
            int rs = instruct.indexOf("R", rd)+1;
            int rt = instruct.indexOf("R", rs)+1;
            register[rd] = register[rs] + register[rt];
        }else if(opocode.equals("SUB")){
            int rd = instruct.indexOf("R")+1;
            int rs = instruct.indexOf("R", rd)+1;
            int rt = instruct.indexOf("R", rs)+1;
            register[rd] = register[rs] - register[rt];
        }else if(opocode.equals("MUL")){
            int rd = instruct.indexOf("R")+1;
            int rs = instruct.indexOf("R", rd)+1;
            int rt = instruct.indexOf("R", rs)+1;
            register[rd] = register[rs] * register[rt];
        }else if(opocode.equals("AND")){
            int rd = instruct.indexOf("R")+1;
            int rs = instruct.indexOf("R", rd)+1;
            int rt = instruct.indexOf("R", rs)+1;
            register[rd] = register[rs] & register[rt];
        }else if(opocode.equals("OR")){
            int rd = instruct.indexOf("R")+1;
            int rs = instruct.indexOf("R", rd)+1;
            int rt = instruct.indexOf("R", rs)+1;
            register[rd] = register[rs] | register[rt];
        }else if(opocode.equals("XOR")){
            int rd = instruct.indexOf("R")+1;
            int rs = instruct.indexOf("R", rd)+1;
            int rt = instruct.indexOf("R", rs)+1;
            register[rd] = register[rs] ^ register[rt];
        }else if(opocode.equals("NOR")){
            int rd = instruct.indexOf("R")+1;
            int rs = instruct.indexOf("R", rd)+1;
            int rt = instruct.indexOf("R", rs)+1;
            register[rd] = ~(register[rs] | register[rt]);
        }else if(opocode.equals("SLT")){
            int rd = instruct.indexOf("R")+1;
            int rs = instruct.indexOf("R", rd)+1;
            int rt = instruct.indexOf("R", rs)+1;
            register[rd] = (register[rs]<register[rt])?1:0;
        }else if(opocode.equals("ADDI")){
            int rt = instruct.indexOf("R")+1;
            int rs = instruct.indexOf("R", rt)+1;
            int immediate_num = instruct.indexOf("#")+1;
            register[rt] = register[rs] + immediate_num;
        }else if(opocode.equals("ANDI")){
            int rt = instruct.indexOf("R")+1;
            int rs = instruct.indexOf("R", rt)+1;
            int immediate_num = instruct.indexOf("#")+1;
            register[rt] = register[rs] & immediate_num;
        }else if(opocode.equals("ORI")){
            int rt = instruct.indexOf("R")+1;
            int rs = instruct.indexOf("R", rt)+1;
            int immediate_num = instruct.indexOf("#")+1;
            register[rt] = register[rs] & immediate_num;
        }
        return address+4;
    }

    public static void main( String[] args )throws Exception
    {
        String line_break = "\r\n";
        // 读取操作系统切换换行符
        if(is_windows()){
            line_break = "\n";
        }
        //读取输入文件
        //String path = args[0];
        String path = "C:\\Users\\JimGrey\\Desktop\\project1\\sample.txt";

        List<String> inputcommand = getInputCommand(path);

        //解析数据
        List<String> outputcommand = new LinkedList<String>();
        List<String> dataList = new LinkedList<String>();
        boolean hasbreak = false;
        int breakcycle = 0;
        for (int i=0; i<inputcommand.size(); i++){
            String output = "";
            String command = inputcommand.get(i);
            if (!hasbreak){
                String category = command.substring(0,2);
                String opcode = "";
                String instuction = "";
                opcode = command.substring(2,6);
                if(category.equals("01")){
                    //category1
                    instuction = getInstruction(true, opcode);
                    if(instuction.equals("BREAK") || instuction.equals("NOP")){
                        output = instuction + line_break;
                        if(instuction.equals("BREAK")){
                            hasbreak = true;
                            breakcycle = i+1;
                        }
                    }else if(instuction.equals("BEQ")){
                        String rs = "R" + binaryToDec('0'+ command.substring(6,11));
                        String rt = "R" + binaryToDec('0'+ command.substring(11,16));
                        String offset = "#" + binaryToDec(command.substring(16,32)+"00");
                        output = instuction + " "+ rs + ", " +rt+", "+offset +line_break;
                    }else if(instuction.equals("BLTZ") ||instuction.equals("BGTZ")){
                        String rs = "R" + binaryToDec('0'+ command.substring(6,11));
                        String offset = "#" + binaryToDec(command.substring(16,32)+"00");
                        output = instuction + " "+ rs + ", " +offset +line_break;
                    }else if(instuction.equals("J")){
                        String instr_index = "#" + binaryToDec(command.substring(6,32)+"00");
                        output = instuction + " " + instr_index + line_break;
                    }else if(instuction.equals("JR")){
                        String rs = "R" + binaryToDec('0'+ command.substring(6,11));
                        output = instuction + " "+ rs + line_break;
                    }else if(instuction.equals("SLL")||instuction.equals("SRL")
                            ||instuction.equals("SRA")){
                        String rt = "R" + binaryToDec('0'+ command.substring(11,16));
                        String rd = "R" + binaryToDec('0'+ command.substring(16,21));
                        String sa = "#" + binaryToDec('0'+ command.substring(21,26));
                        output = instuction + " "+rd+", "+rt+", "+sa+line_break;
                    }else if(instuction.equals("LW")||instuction.equals("SW")){
                        String base = "R" + binaryToDec('0'+ command.substring(6,11));
                        String rt = "R" + binaryToDec('0'+ command.substring(11,16));
                        String offset = binaryToDec('0'+command.substring(16,32));
                        output = instuction + " "+ rt +", "+offset+"("+base+")\n";
                    }
                }else{
                    //category2
                    instuction = getInstruction(false, opcode);
                    String rs = "R" + binaryToDec('0'+command.substring(6,11));
                    String rt = "R" + binaryToDec('0'+command.substring(11,16));

                    if(instuction.equals("ADDI")||instuction.equals("ANDI")
                            ||instuction.equals("ORI")||instuction.equals("XORI")){
                        String immediatenum = "#"+ binaryToDec(command.substring(16,32));
                        output = instuction+" "+ rt +", "+ rs + ", "+ immediatenum + line_break;
                    }else{
                        String rd = "R" + binaryToDec('0'+command.substring(16,21));
                        output = instuction+" "+ rd +", "+ rs + ", "+ rt +line_break;
                    }

                }
            }else{
                String tempoutput = binaryToDec(command);
                output = tempoutput + line_break;
                dataList.add(tempoutput);
            }
            outputcommand.add(output);
        }
        //数据解析完毕,输出到文件
        File disassembly_file = new File("disassembly.txt");
        if(!disassembly_file.exists()||!disassembly_file.isFile()){
            disassembly_file.createNewFile();
        }else{
            disassembly_file.delete();
            disassembly_file.createNewFile();
        }

        BufferedWriter outScream  =
                new BufferedWriter(new FileWriter(disassembly_file));

        Map<String,String> instuctionmap = new HashMap<String,String >();

        for(int i=0;i<inputcommand.size();i++){
            String address = Integer.toString(256 + 4*i);
            outScream.append(inputcommand.get(i)+"\t"+address+"\t"+outputcommand.get(i));
            instuctionmap.put(address,outputcommand.get(i));
        }
        outScream.close();

        //disassembly.txt has finished
        int cycle= 0;
        boolean hasfinished = false;
        int now_address = 256;
        int[] register = new int[32];

        File simulation_file = new File("simulation.txt");
        outScream = new BufferedWriter(new FileWriter(simulation_file));
        while(!hasfinished){
            cycle=cycle+1;
            int new_now_address = execute_instrucion(now_address, register,
                    instuctionmap);

            //开始simulation_print
            for(int i=0; i<20;i++){
                outScream.append('-');
            }
            outScream.append('\n');

            outScream.append("Cycle:"+cycle+"\t" +now_address+"\t"
                    +instuctionmap.get(Integer.toString(now_address).replaceAll(" ","\t")));

            outScream.append(line_break);
            outScream.append("Registers\n");
            String register_info = "";
            for(int i=0; i<4; i++){
                register_info = register_info + "R" + String.format("%02d", i*8) + ":";
                for(int j=0;j<8;j++){
                    register_info = register_info + "\t" + register[i*8+j];
                }
                register_info = register_info + line_break;
            }
            outScream.append(register_info);
            outScream.append(line_break);

            outScream.append("Data\n");
            int print_address = 256 + breakcycle * 4;
            String data_info = "";
            for (int i=0; i<dataList.size();i++) {
                if(i%8==0){
                    data_info = data_info + print_address+":";
                }
                data_info = data_info + "\t" + dataList.get(i);
                if(i%8==7){
                    data_info = data_info + line_break;
                }
            }
            now_address = new_now_address;
            outScream.append(data_info);
        }
        outScream.close();
    }
}
