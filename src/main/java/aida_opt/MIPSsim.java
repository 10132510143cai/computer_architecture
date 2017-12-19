import java.io.*;
import java.util.*;

/**
 * On my honor, I have neither given nor received unauthorized aid on this assignment.
 * 51174500002
 */
public class MIPSsim {
    /**
     * @return whether it is windows operator system
     */
    public static boolean is_windows() {
        Properties props = System.getProperties();
        String operation_system = props.getProperty("os.name");
        operation_system = operation_system.toLowerCase();
        int index = operation_system.indexOf("windows");
        return (index >= 0);
    }

    /**
     * get input
     *
     * @param path path of file
     * @return list of instruct
     * @throws Exception
     */
    public static List<String> getInputCommand(String path) throws Exception {
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
     * from binary to dec
     *
     * @param binary binary string
     * @return dec string
     */
    public static String binaryToDec(String binary) {
        boolean isnegative = false;
        if (binary.startsWith("1")) {
            isnegative = true;
            char[] c = binary.toCharArray();
            for (int i = 0; i < binary.length(); i++) {
                if (Character.getNumericValue(c[i]) == 0) {
                    c[i] = '1';
                } else {
                    c[i] = '0';
                }
            }

            boolean finded = false;
            for (int i = binary.length() - 1; i >= 0 && !finded; i--) {
                if (Character.getNumericValue(c[i]) == 0) {
                    c[i] = '1';
                    finded = true;
                } else {
                    c[i] = '0';
                }
            }
            binary = String.valueOf(c);
        }
        String dec = "";
        if (binary.equals("10000000000000000000000000000000")) {
            dec = Integer.toString(Integer.MIN_VALUE);
            isnegative = false;
        } else {
            dec = Integer.toString(Integer.parseInt(binary, 2));
        }

        if (isnegative) {
            dec = "-" + dec;
        }
        return dec;
    }

    /**
     * decode instrucation
     *
     * @param isCategory1
     * @param identification_bits
     * @return
     */
    public static String getInstruction(boolean isCategory1, String identification_bits) {
        if (isCategory1) {
            if (identification_bits.equals("0000")) {
                return "J";
            } else if (identification_bits.equals("0001")) {
                return "JR";
            } else if (identification_bits.equals("0010")) {
                return "BEQ";
            } else if (identification_bits.equals("0011")) {
                return "BLTZ";
            } else if (identification_bits.equals("0100")) {
                return "BGTZ";
            } else if (identification_bits.equals("0101")) {
                return "BREAK";
            } else if (identification_bits.equals("0110")) {
                return "SW";
            } else if (identification_bits.equals("0111")) {
                return "LW";
            } else if (identification_bits.equals("1000")) {
                return "SLL";
            } else if (identification_bits.equals("1001")) {
                return "SRL";
            } else if (identification_bits.equals("1010")) {
                return "SRA";
            } else if (identification_bits.equals("1011")) {
                return "NOP";
            } else {
                return "ERROR";
            }
        } else {
            if (identification_bits.equals("0000")) {
                return "ADD";
            } else if (identification_bits.equals("0001")) {
                return "SUB";
            } else if (identification_bits.equals("0010")) {
                return "MUL";
            } else if (identification_bits.equals("0011")) {
                return "AND";
            } else if (identification_bits.equals("0100")) {
                return "OR";
            } else if (identification_bits.equals("0101")) {
                return "XOR";
            } else if (identification_bits.equals("0110")) {
                return "NOR";
            } else if (identification_bits.equals("0111")) {
                return "SLT";
            } else if (identification_bits.equals("1000")) {
                return "ADDI";
            } else if (identification_bits.equals("1001")) {
                return "ANDI";
            } else if (identification_bits.equals("1010")) {
                return "ORI";
            } else if (identification_bits.equals("1011")) {
                return "XORI";
            } else {
                return "ERROR";
            }
        }
    }

    /**
     * @param address
     * @param register
     * @param instuctionmap instruction Hashmap
     * @return
     */
    public static int execute_instrucion(int address, int[] register, List<String> dataList, int datastart,
                                         Map<String, String> instuctionmap, boolean hasfinished) {
        String[] instructList = getinstructList(instuctionmap, address);
        address = address + 4;
        String opocode = instructList[0];

        if (opocode.equals("ADD")) {
            int rd = Integer.parseInt(instructList[1].substring(1, instructList[1].length() - 1));
            int rs = Integer.parseInt(instructList[2].substring(1, instructList[2].length() - 1));
            int rt = Integer.parseInt(instructList[3].substring(1, instructList[3].length()));
            register[rd] = register[rs] + register[rt];
        } else if (opocode.equals("SUB")) {
            int rd = Integer.parseInt(instructList[1].substring(1, instructList[1].length() - 1));
            int rs = Integer.parseInt(instructList[2].substring(1, instructList[2].length() - 1));
            int rt = Integer.parseInt(instructList[3].substring(1, instructList[3].length()));
            register[rd] = register[rs] - register[rt];
        } else if (opocode.equals("MUL")) {
            int rd = Integer.parseInt(instructList[1].substring(1, instructList[1].length() - 1));
            int rs = Integer.parseInt(instructList[2].substring(1, instructList[2].length() - 1));
            int rt = Integer.parseInt(instructList[3].substring(1, instructList[3].length()));
            register[rd] = register[rs] * register[rt];
        } else if (opocode.equals("AND")) {
            int rd = Integer.parseInt(instructList[1].substring(1, instructList[1].length() - 1));
            int rs = Integer.parseInt(instructList[2].substring(1, instructList[2].length() - 1));
            int rt = Integer.parseInt(instructList[3].substring(1, instructList[3].length()));
            register[rd] = register[rs] & register[rt];
        } else if (opocode.equals("OR")) {
            int rd = Integer.parseInt(instructList[1].substring(1, instructList[1].length() - 1));
            int rs = Integer.parseInt(instructList[2].substring(1, instructList[2].length() - 1));
            int rt = Integer.parseInt(instructList[3].substring(1, instructList[3].length()));
            register[rd] = register[rs] | register[rt];
        } else if (opocode.equals("XOR")) {
            int rd = Integer.parseInt(instructList[1].substring(1, instructList[1].length() - 1));
            int rs = Integer.parseInt(instructList[2].substring(1, instructList[2].length() - 1));
            int rt = Integer.parseInt(instructList[3].substring(1, instructList[3].length()));
            register[rd] = register[rs] ^ register[rt];
        } else if (opocode.equals("NOR")) {
            int rd = Integer.parseInt(instructList[1].substring(1, instructList[1].length() - 1));
            int rs = Integer.parseInt(instructList[2].substring(1, instructList[2].length() - 1));
            int rt = Integer.parseInt(instructList[3].substring(1, instructList[3].length()));
            register[rd] = ~(register[rs] | register[rt]);
        } else if (opocode.equals("SLT")) {
            int rd = Integer.parseInt(instructList[1].substring(1, instructList[1].length() - 1));
            int rs = Integer.parseInt(instructList[2].substring(1, instructList[2].length() - 1));
            int rt = Integer.parseInt(instructList[3].substring(1, instructList[3].length()));
            register[rd] = (register[rs] < register[rt]) ? 1 : 0;
        } else if (opocode.equals("ADDI")) {
            int rt = Integer.parseInt(instructList[1].substring(1, instructList[1].length() - 1));
            int rs = Integer.parseInt(instructList[2].substring(1, instructList[2].length() - 1));
            int immediate_num = Integer.parseInt(instructList[3].substring(1, instructList[3].length()));
            register[rt] = register[rs] + immediate_num;
        } else if (opocode.equals("ANDI")) {
            int rt = Integer.parseInt(instructList[1].substring(1, instructList[1].length() - 1));
            int rs = Integer.parseInt(instructList[2].substring(1, instructList[2].length() - 1));
            int immediate_num = Integer.parseInt(instructList[3].substring(1, instructList[3].length()));
            register[rt] = register[rs] & immediate_num;
        } else if (opocode.equals("ORI")) {
            int rt = Integer.parseInt(instructList[1].substring(1, instructList[1].length() - 1));
            int rs = Integer.parseInt(instructList[2].substring(1, instructList[2].length() - 1));
            int immediate_num = Integer.parseInt(instructList[3].substring(1, instructList[3].length()));
            register[rt] = register[rs] & immediate_num;
        } else if (opocode.equals("XORI")) {
            int rt = Integer.parseInt(instructList[1].substring(1, instructList[1].length() - 1));
            int rs = Integer.parseInt(instructList[2].substring(1, instructList[2].length() - 1));
            int immediate_num = Integer.parseInt(instructList[3].substring(1, instructList[3].length()));
            register[rt] = register[rs] ^ immediate_num;
        } else if (opocode.equals("BEQ")) {
            int rs = Integer.parseInt(instructList[1].substring(1, instructList[1].length() - 1));
            int rt = Integer.parseInt(instructList[2].substring(1, instructList[2].length() - 1));
            int offset = Integer.parseInt(instructList[3].substring(1, instructList[3].length()));

            if (register[rs] == register[rt]) {
                address = address + offset;
            }
        } else if (opocode.equals("BLTZ")) {
            int rs = Integer.parseInt(instructList[1].substring(1, instructList[1].length() - 1));
            int offset = Integer.parseInt(instructList[2].substring(1, instructList[2].length()));

            if (register[rs] < 0) {
                address = address + offset;
            }
        } else if (opocode.equals("BGTZ")) {
            int rs = Integer.parseInt(instructList[1].substring(1, instructList[1].length() - 1));
            int offset = Integer.parseInt(instructList[2].substring(1, instructList[2].length()));
            if (register[rs] > 0) {
                address = address + offset;
            }
        } else if (opocode.equals("J")) {
            int instr_index = Integer.parseInt(instructList[1].substring(1, instructList[1].length()));
            address = instr_index;
        } else if (opocode.equals("JR")) {
            int rs = Integer.parseInt(instructList[1].substring(1, instructList[1].length()));
            address = register[rs];
        } else if (opocode.equals("SW")) {
            int rt = Integer.parseInt(instructList[1].substring(1, instructList[1].length() - 1));
            int offset = Integer.parseInt(instructList[2].substring(0, instructList[2].indexOf("(")));
            int base = Integer.parseInt(instructList[2].substring(instructList[2].indexOf("(") + 2, instructList[2].indexOf(")")));
            int list_index = (register[base] + offset - datastart) / 4;
            dataList.set(list_index, Integer.toString(register[rt]));
        } else if (opocode.equals("LW")) {
            int rt = Integer.parseInt(instructList[1].substring(1, instructList[1].length() - 1));
            int offset = Integer.parseInt(instructList[2].substring(0, instructList[2].indexOf("(")));
            int base = Integer.parseInt(instructList[2].substring(instructList[2].indexOf("(") + 2, instructList[2].indexOf(")")));

            int list_index = (register[base] + offset - datastart) / 4;
            String value = dataList.get(list_index);
            register[rt] = Integer.parseInt(value);
        } else if (opocode.equals("SLL")) {
            int rd = Integer.parseInt(instructList[1].substring(1, instructList[1].length() - 1));
            int rt = Integer.parseInt(instructList[2].substring(1, instructList[2].length() - 1));
            int sa = Integer.parseInt(instructList[3].substring(1, instructList[3].length()));
            register[rd] = register[rt] << sa;
        } else if (opocode.equals("SRL")) {
            int rd = Integer.parseInt(instructList[1].substring(1, instructList[1].length() - 1));
            int rt = Integer.parseInt(instructList[2].substring(1, instructList[2].length() - 1));
            int sa = Integer.parseInt(instructList[3].substring(1, instructList[3].length()));
            register[rd] = register[rt] >>> sa;
        } else if (opocode.equals("SRA")) {
            int rd = Integer.parseInt(instructList[1].substring(1, instructList[1].length() - 1));
            int rt = Integer.parseInt(instructList[2].substring(1, instructList[2].length() - 1));
            int sa = Integer.parseInt(instructList[3].substring(1, instructList[3].length()));
            register[rd] = register[rt] >> sa;
        } else if (opocode.equals("BREAK")) {
            address = -1;
            hasfinished = true;
        }
        return address;
    }

    public static String[] getinstructList(Map<String, String> instuctionmap, int address) {
        String instruct = "BREAK";
        if (instuctionmap.containsKey(Integer.toString(address))) {
            instruct = instuctionmap.get(Integer.toString(address));
            if (instruct.contains("\n")) {
                instruct = instruct.replace("\n", "");
            }
            if (instruct.contains("\r")) {
                instruct = instruct.replace("\r", "");
            }
        }
        return instruct.split(" ");
    }

    public static void RenewQueue(List<Integer> newPre_Issue_Queue, List<Integer> oldPre_Issue) {
        for (int i = 0; i < oldPre_Issue.size(); i++) {
            newPre_Issue_Queue.add(i, oldPre_Issue.get(i));
        }
    }

    public static void RenewList(List<String> newPre_Issue_Queue, List<String> oldPre_Issue) {
        for (int i = 0; i < oldPre_Issue.size(); i++) {
            newPre_Issue_Queue.add(i, oldPre_Issue.get(i));
        }
    }

    public static int excutePipeline(int address, int[] register, List<String> dataList, int breakcycle,
                                     Map<String, String> instuctionmap, boolean[] hasfinished, int[] IF_Unit,
                                     Map<String, List<Integer>> Queue_Map, int pipelineaddress,
                                     boolean issuehasfinished, int[] registerinuse) {
        //checkreadorwrite(Queue_Map, registerinuse);
        boolean[] tempregisterneedread = new boolean[32];

        // IF UNIT
        boolean shouldwait = false;
        if (IF_Unit[0] != 0) {
            shouldwait = true;
            int tempIF_Unit[] = {IF_Unit[0]};
            if (optionisready(registerinuse, register, tempIF_Unit, instuctionmap)) {
                IF_Unit[1] = IF_Unit[0];
                IF_Unit[0] = 0;
            }
        } else if (IF_Unit[1] != 0) {
            pipelineaddress = execute_instrucion(IF_Unit[1], register, dataList, 256 + 4 * breakcycle,
                    instuctionmap, hasfinished[0]);
            IF_Unit[1] = 0;
        }

        // pre issue
        List<Integer> newPre_Issue_Queue = new LinkedList<Integer>();
        RenewQueue(newPre_Issue_Queue, Queue_Map.get("Pre_Issue_Queue"));
        if (!shouldwait) {
            boolean firstisbranch = false;
            for (int i = 0; i < 2 && !firstisbranch; i++) {
                if (IF_Unit[0] == 0) {
                    if (newPre_Issue_Queue.size() < 4 && !hasfinished[0]) {
                        String[] instructList = getinstructList(instuctionmap, pipelineaddress);
                        String opocode = instructList[0];
                        int newpipineaddress = pipelineaddress;
                        pipelineaddress = pipelineaddress + 4;

                        if (opocode.equals("J") || opocode.equals("JR") || opocode.equals("BEQ") ||
                                opocode.equals("BLTZ") || opocode.equals("BGTZ") || opocode.equals("BREAK")) {
                            firstisbranch = true;
                            int tempaddress[] = {newpipineaddress};
                            if (optionisready(registerinuse, register, tempaddress, instuctionmap)) {
                                IF_Unit[1] = newpipineaddress;

                            } else {
                                IF_Unit[0] = newpipineaddress;
                            }
                            if(opocode.equals("BREAK")){
                                hasfinished[0] = true;
                            }
                        } else {
                            newPre_Issue_Queue.add(newpipineaddress);
                            for(int j=0;j<newPre_Issue_Queue.size();j++){
                                String[] tempinstructList = getinstructList(instuctionmap, newPre_Issue_Queue.get(j));
                                String tempopocode = tempinstructList[0];
                                if (tempopocode.equals("ADD") || tempopocode.equals("SUB") || tempopocode.equals("MUL") || tempopocode.equals("AND") ||
                                        tempopocode.equals("OR") || tempopocode.equals("XOR") || tempopocode.equals("NOR") || tempopocode.equals("SLT")) {
                                    int rd = Integer.parseInt(tempinstructList[1].substring(1, tempinstructList[1].length() - 1));
                                    int rs = Integer.parseInt(tempinstructList[2].substring(1, tempinstructList[2].length() - 1));
                                    int rt = Integer.parseInt(tempinstructList[3].substring(1, tempinstructList[3].length()));

                                    tempregisterneedread[rt] = true;
                                    tempregisterneedread[rs] = true;
                                }else if (tempopocode.equals("SLL") || tempopocode.equals("SRA") || tempopocode.equals("SRL")) {
                                    int rd = Integer.parseInt(tempinstructList[1].substring(1, tempinstructList[1].length() - 1));
                                    int rt = Integer.parseInt(tempinstructList[2].substring(1, tempinstructList[2].length() - 1));

                                    tempregisterneedread[rt] = true;
                                }else if (tempopocode.equals("LW") || tempopocode.equals("SW")) {
                                    int rt = Integer.parseInt(tempinstructList[1].substring(1, tempinstructList[1].length() - 1));
                                    int offset = Integer.parseInt(tempinstructList[2].substring(0, tempinstructList[2].indexOf("(")));
                                    int base = Integer.parseInt(tempinstructList[2].substring(tempinstructList[2].indexOf("(") + 2, tempinstructList[2].indexOf(")")));

                                    tempregisterneedread[base] = true;
                                }
                            }

                            if (opocode.equals("ADD") || opocode.equals("SUB") || opocode.equals("MUL") || opocode.equals("AND") ||
                                    opocode.equals("OR") || opocode.equals("XOR") || opocode.equals("NOR") || opocode.equals("SLT")) {
                                int rd = Integer.parseInt(instructList[1].substring(1, instructList[1].length() - 1));
                                int rs = Integer.parseInt(instructList[2].substring(1, instructList[2].length() - 1));
                                int rt = Integer.parseInt(instructList[3].substring(1, instructList[3].length()));
                                if (registerinuse[rd] == 0 && !tempregisterneedread[rd]) {
                                    registerinuse[rd] = newpipineaddress;
                                }
                                tempregisterneedread[rt] = true;
                                tempregisterneedread[rs] = true;
                            }else if (opocode.equals("SLL") || opocode.equals("SRA") || opocode.equals("SRL")) {
                                int rd = Integer.parseInt(instructList[1].substring(1, instructList[1].length() - 1));
                                int rt = Integer.parseInt(instructList[2].substring(1, instructList[2].length() - 1));

                                if (registerinuse[rd] == 0 && !tempregisterneedread[rd]) {
                                    registerinuse[rd] = newpipineaddress;
                                }
                                tempregisterneedread[rt] = true;
                            }else if (opocode.equals("LW") || opocode.equals("SW")) {
                                int rt = Integer.parseInt(instructList[1].substring(1, instructList[1].length() - 1));
                                int offset = Integer.parseInt(instructList[2].substring(0, instructList[2].indexOf("(")));
                                int base = Integer.parseInt(instructList[2].substring(instructList[2].indexOf("(") + 2, instructList[2].indexOf(")")));

                                if (registerinuse[rt] == 0 && !tempregisterneedread[rt]) {
                                    registerinuse[rt] = newpipineaddress;
                                }
                                tempregisterneedread[base] = true;
                            }
                            /*int rd = Integer.parseInt(instructList[1].substring(1, instructList[1].length() - 1));
                            if (registerinuse[rd] == 0) {
                                registerinuse[rd] = newpipineaddress;
                            }*/
                        }

                        if (opocode.equals("BREAK")) {
                            issuehasfinished = true;
                        }
                    }
                }
            }
        }
        // pre issue over

        // issue start
        boolean canloadstore = true;
        boolean cannonloadstore = true;
        boolean nooperation = false;
        boolean[] registerneedread = new boolean[32];
        List<Integer> Pre_Issue_Queue = Queue_Map.get("Pre_Issue_Queue");

        List<Integer> newPre_ALU1_Queue = new LinkedList<Integer>();
        List<Integer> newPre_ALU2_Queue = new LinkedList<Integer>();
        RenewQueue(newPre_ALU1_Queue, Queue_Map.get("Pre_ALU1_Queue"));
        RenewQueue(newPre_ALU2_Queue, Queue_Map.get("Pre_ALU2_Queue"));

        boolean hasstorebefore = false;
        for (int i = 0; i < Pre_Issue_Queue.size(); i++) {
            int issueaddress = Pre_Issue_Queue.get(i);
            String[] instructList = getinstructList(instuctionmap, issueaddress);
            String opocode = instructList[0];
            if (opocode.equals("ADD") || opocode.equals("SUB") || opocode.equals("MUL") || opocode.equals("AND") ||
                    opocode.equals("OR") || opocode.equals("XOR") || opocode.equals("NOR") || opocode.equals("SLT")) {
                int rd = Integer.parseInt(instructList[1].substring(1, instructList[1].length() - 1));
                int rs = Integer.parseInt(instructList[2].substring(1, instructList[2].length() - 1));
                int rt = Integer.parseInt(instructList[3].substring(1, instructList[3].length()));

                if (registerinuse[rd] == 0 && !registerneedread[rd]) {
                    registerinuse[rd] = issueaddress;
                }

                if (registerinuse[rd] == issueaddress && registerinuse[rs] == 0 && registerinuse[rt] == 0 && cannonloadstore &&
                        newPre_ALU2_Queue.size() < 2) {
                    int al2_size = newPre_ALU2_Queue.size();
                    newPre_ALU2_Queue.add(al2_size, issueaddress);
                    newPre_Issue_Queue.remove(i);
                    cannonloadstore = false;
                }
                registerneedread[rs] = true;
                registerneedread[rt] = true;

            } else if (opocode.equals("LW") || opocode.equals("SW")) {
                int rt = Integer.parseInt(instructList[1].substring(1, instructList[1].length() - 1));
                int offset = Integer.parseInt(instructList[2].substring(0, instructList[2].indexOf("(")));
                int base = Integer.parseInt(instructList[2].substring(instructList[2].indexOf("(") + 2, instructList[2].indexOf(")")));

                if (registerinuse[rt] == 0 && !registerneedread[rt]) {
                    registerinuse[rt] = issueaddress;
                }

                if (registerinuse[rt] == issueaddress && (registerinuse[base] == 0||registerinuse[base] == issueaddress) && canloadstore && newPre_ALU1_Queue.size() < 2 && !registerneedread[rt] &&!hasstorebefore) {
                    int al1_size = newPre_ALU1_Queue.size();
                    newPre_ALU1_Queue.add(al1_size, issueaddress);
                    newPre_Issue_Queue.remove(i);
                    canloadstore = false;
                }

                if(opocode.equals("SW"))
                    hasstorebefore = true;

                registerneedread[base] = true;
            } else if (opocode.equals("SLL") || opocode.equals("SRA") || opocode.equals("SRL")) {
                int rd = Integer.parseInt(instructList[1].substring(1, instructList[1].length() - 1));
                int rt = Integer.parseInt(instructList[2].substring(1, instructList[2].length() - 1));

                if (registerinuse[rd] == 0 && !registerneedread[rd]) {
                    registerinuse[rd] = issueaddress;
                }
                if (registerinuse[rd] == issueaddress && (registerinuse[rt] == 0|| registerinuse[rt] == issueaddress)&& cannonloadstore &&
                        newPre_ALU2_Queue.size() < 2 && !registerneedread[rd]) {
                    int al2_size = newPre_ALU2_Queue.size();
                    newPre_ALU2_Queue.add(al2_size, issueaddress);
                    newPre_Issue_Queue.remove(i);
                    cannonloadstore = false;
                }
                registerneedread[rt] = true;
            } else if(opocode.equals("ADDI")||opocode.equals("ANDI")||opocode.equals("ORI")||opocode.equals("XORI")){
                int rt = Integer.parseInt(instructList[1].substring(1, instructList[1].length() - 1));
                int rs = Integer.parseInt(instructList[2].substring(1, instructList[2].length() - 1));
                if (registerinuse[rt] == 0 && !registerneedread[rt]) {
                    registerinuse[rt] = issueaddress;
                }

                if(registerinuse[rt] == issueaddress && (registerinuse[rs]==0||registerinuse[rs]==issueaddress)&& cannonloadstore && newPre_ALU2_Queue.size()<2){
                    int al2_size = newPre_ALU2_Queue.size();
                    newPre_ALU2_Queue.add(al2_size, issueaddress);
                    newPre_Issue_Queue.remove(i);
                    cannonloadstore = false;
                }
                registerneedread[rs] = true;

            }
        }
        //Issue start end

        //Compute start
        List<Integer> Pre_ALU1_Queue = Queue_Map.get("Pre_ALU1_Queue");

        List<Integer> newPre_Mem_Queue = new LinkedList<Integer>();
        RenewQueue(newPre_Mem_Queue, Queue_Map.get("Pre_Mem_Queue"));

        //pre_alu1->pre-mem
        if(newPre_Mem_Queue.size()>0){
            newPre_Mem_Queue.remove(0);
        }

        if (Pre_ALU1_Queue.size() > 0) {
            newPre_Mem_Queue.add(0, Pre_ALU1_Queue.get(0));
            newPre_ALU1_Queue.remove(0);
        }

        List<Integer> newPost_Mem_Queue = new LinkedList<Integer>();
        RenewQueue(newPost_Mem_Queue, Queue_Map.get("Post_Mem_Queue"));
        List<Integer> Pre_Mem_Queue = Queue_Map.get("Pre_Mem_Queue");

        //pre-mem->post-mem
        if(newPost_Mem_Queue.size()>0){
            newPost_Mem_Queue.remove(0);
        }

        if (Pre_Mem_Queue.size() > 0) {
            int pre_men_queuenum = Pre_Mem_Queue.get(0);
            String[] Pre_Mem_QueueinstructList = getinstructList(instuctionmap, pre_men_queuenum);
            int new_now_address = execute_instrucion(Pre_Mem_Queue.get(0), register, dataList, 256 + 4 * breakcycle,
                    instuctionmap, hasfinished[0]);

            if (new_now_address < 0) {
                hasfinished[0] = true;
            }

            String pre_opocode = Pre_Mem_QueueinstructList[0];

            if (pre_opocode.equals("LW")) {
                newPost_Mem_Queue.add(0, Pre_Mem_Queue.get(0));
                Queue_Map.get("Pre_Mem_Queue").remove(0);
            } else {
                Queue_Map.get("Pre_Mem_Queue").remove(0);
                int rt = Integer.parseInt(Pre_Mem_QueueinstructList[1].substring(1, Pre_Mem_QueueinstructList[1].length() - 1));
                registerinuse[rt] = 0;
            }
        }
        //pre-alu2->post-alu2
        List<Integer> Pre_ALU2_Queue = Queue_Map.get("Pre_ALU2_Queue");

        List<Integer> newPost_ALU2_Queue = new LinkedList<Integer>();
        RenewQueue(newPost_ALU2_Queue, Queue_Map.get("Post_ALU2_Queue"));

        if(newPost_ALU2_Queue.size()>0){
            newPost_ALU2_Queue.remove(0);
        }

        if (Pre_ALU2_Queue.size() > 0) {
            String[] instructList = getinstructList(instuctionmap, Pre_ALU2_Queue.get(0));
            String opocode = instructList[0];

            int new_now_address = execute_instrucion(Pre_ALU2_Queue.get(0), register, dataList, 256 + 4 * breakcycle,
                    instuctionmap, hasfinished[0]);

            newPost_ALU2_Queue.add(0, Pre_ALU2_Queue.get(0));
            newPre_ALU2_Queue.remove(0);
        }
        //post mem start
        List<Integer> Post_Mem_Queue = Queue_Map.get("Post_Mem_Queue");
        for(int i=0;i<Post_Mem_Queue.size();i++){
            String[] instructList = getinstructList(instuctionmap, Post_Mem_Queue.get(i));
            String opocode = instructList[0];
            int rt = Integer.parseInt(instructList[1].substring(1, instructList[1].length() - 1));
            registerinuse[rt] = 0;
        }
        //post mem end

        //post alu2 start
        List<Integer> Post_ALU2_Queue = Queue_Map.get("Post_ALU2_Queue");
        for(int i=0;i<Post_ALU2_Queue.size();i++){
            String[] instructList = getinstructList(instuctionmap, Post_ALU2_Queue.get(i));
            String opocode = instructList[0];
            int rd = Integer.parseInt(instructList[1].substring(1, instructList[1].length() - 1));
            registerinuse[rd] = 0;
        }
        //post alu2 end
        //Compute end

        //update
        Queue_Map.replace("Pre_Issue_Queue", newPre_Issue_Queue);
        Queue_Map.replace("Pre_ALU1_Queue", newPre_ALU1_Queue);
        Queue_Map.replace("Pre_Mem_Queue", newPre_Mem_Queue);
        Queue_Map.replace("Post_Mem_Queue", newPost_Mem_Queue);
        Queue_Map.replace("Pre_ALU2_Queue", newPre_ALU2_Queue);
        Queue_Map.replace("Post_ALU2_Queue", newPost_ALU2_Queue);

        return pipelineaddress;
    }

    public static boolean optionisready(int[] registerinuse, int[] register, int[] address, Map<String, String> instuctionmap) {
        String[] instructList = getinstructList(instuctionmap, address[0]);
        String opocode = instructList[0];
        if (opocode.equals("BREAK")) {
            return true;
        } else if (opocode.equals("J")) {
            int instr_index = Integer.parseInt(instructList[1].substring(1, instructList[1].length()));
            address[0] = instr_index;
            return true;
        } else if (opocode.equals("JR")) {
            int rs = Integer.parseInt(instructList[1].substring(1, instructList[1].length()));
            if (registerinuse[rs] == 0) {
                address[0] = register[rs];
                return true;
            } else {
                return false;
            }
        } else if (opocode.equals("BEQ")) {
            int rs = Integer.parseInt(instructList[1].substring(1, instructList[1].length() - 1));
            int rt = Integer.parseInt(instructList[2].substring(1, instructList[2].length() - 1));
            int offset = Integer.parseInt(instructList[3].substring(1, instructList[3].length()));
            if (registerinuse[rs] == 0 && registerinuse[rt] == 0) {
                if (register[rs] == register[rt]) {
                    address[0] = address[0] + offset;
                } else {
                    address[0] = address[0] + 4;
                }
                return true;
            } else {
                return false;
            }
        } else if (opocode.equals("BLTZ")) {
            int rs = Integer.parseInt(instructList[1].substring(1, instructList[1].length() - 1));
            int offset = Integer.parseInt(instructList[2].substring(1, instructList[2].length()));
            if (registerinuse[rs] == 0) {
                if (register[rs] < 0) {
                    address[0] = address[0] + offset;
                } else {
                    address[0] = address[0] + 4;
                }
                return true;
            } else {
                return false;
            }
        } else if (opocode.equals("BGTZ")) {
            int rs = Integer.parseInt(instructList[1].substring(1, instructList[1].length() - 1));
            int offset = Integer.parseInt(instructList[2].substring(1, instructList[2].length()));
            if (registerinuse[rs] == 0) {
                if (register[rs] > 0) {
                    address[0] = address[0] + offset;
                } else {
                    address[0] = address[0] + 4;
                }
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public static void printRegisterandData(BufferedWriter outScream, String line_break, int[] register, int breakcycle,
                                            List<String> dataList) throws Exception {
        outScream.append("Registers" + line_break);
        String register_info = "";
        for (int i = 0; i < 4; i++) {
            register_info = register_info + "R" + String.format("%02d", i * 8) + ":";
            for (int j = 0; j < 8; j++) {
                register_info = register_info + "\t" + register[i * 8 + j];
            }
            register_info = register_info + line_break;
        }
        outScream.append(register_info);
        outScream.append(line_break);

        outScream.append("Data" + line_break);
        int print_address = 256 + breakcycle * 4;
        String data_info = "";
        for (int i = 0; i < dataList.size(); i++) {
            if (i % 8 == 0) {
                data_info = data_info + (print_address + i * 4) + ":";
            }
            data_info = data_info + "\t" + dataList.get(i);
            if (i % 8 == 7) {
                data_info = data_info + line_break;
            }
        }
        outScream.append(data_info);
        outScream.append(line_break);
    }

    public static void main(String[] args) throws Exception {
        String line_break = "\n";

        if (is_windows()) {
            line_break = "\r\n";
        }

        //String path = args[0];
        String path = "sample2.txt";

        List<String> inputcommand = getInputCommand(path);


        List<String> outputcommand = new LinkedList<String>();
        List<String> dataList = new LinkedList<String>();
        boolean hasbreak = false;
        int breakcycle = 0;
        for (int i = 0; i < inputcommand.size(); i++) {
            String output = "";
            String command = inputcommand.get(i);
            if (!hasbreak) {
                String category = command.substring(0, 2);
                String opcode = "";
                String instuction = "";
                opcode = command.substring(2, 6);
                if (category.equals("01")) {
                    //category1
                    instuction = getInstruction(true, opcode);
                    if (instuction.equals("BREAK") || instuction.equals("NOP")) {
                        output = instuction + line_break;
                        if (instuction.equals("BREAK")) {
                            hasbreak = true;
                            breakcycle = i + 1;
                        }
                    } else if (instuction.equals("BEQ")) {
                        String rs = "R" + binaryToDec('0' + command.substring(6, 11));
                        String rt = "R" + binaryToDec('0' + command.substring(11, 16));
                        String offset = "#" + binaryToDec(command.substring(16, 32) + "00");
                        output = instuction + " " + rs + ", " + rt + ", " + offset + line_break;
                    } else if (instuction.equals("BLTZ") || instuction.equals("BGTZ")) {
                        String rs = "R" + binaryToDec('0' + command.substring(6, 11));
                        String offset = "#" + binaryToDec(command.substring(16, 32) + "00");
                        output = instuction + " " + rs + ", " + offset + line_break;
                    } else if (instuction.equals("J")) {
                        String instr_index = "#" + binaryToDec(command.substring(6, 32) + "00");
                        output = instuction + " " + instr_index + line_break;
                    } else if (instuction.equals("JR")) {
                        String rs = "R" + binaryToDec('0' + command.substring(6, 11));
                        output = instuction + " " + rs + line_break;
                    } else if (instuction.equals("SLL") || instuction.equals("SRL")
                            || instuction.equals("SRA")) {
                        String rt = "R" + binaryToDec('0' + command.substring(11, 16));
                        String rd = "R" + binaryToDec('0' + command.substring(16, 21));
                        String sa = "#" + binaryToDec('0' + command.substring(21, 26));
                        output = instuction + " " + rd + ", " + rt + ", " + sa + line_break;
                    } else if (instuction.equals("LW") || instuction.equals("SW")) {
                        String base = "R" + binaryToDec('0' + command.substring(6, 11));
                        String rt = "R" + binaryToDec('0' + command.substring(11, 16));
                        String offset = binaryToDec('0' + command.substring(16, 32));
                        output = instuction + " " + rt + ", " + offset + "(" + base + ")" + line_break;
                    }
                } else {
                    //category2
                    instuction = getInstruction(false, opcode);
                    String rs = "R" + binaryToDec('0' + command.substring(6, 11));
                    String rt = "R" + binaryToDec('0' + command.substring(11, 16));

                    if (instuction.equals("ADDI") || instuction.equals("ANDI")
                            || instuction.equals("ORI") || instuction.equals("XORI")) {
                        String immediatenum = "#" + binaryToDec(command.substring(16, 32));
                        output = instuction + " " + rt + ", " + rs + ", " + immediatenum + line_break;
                    } else {
                        String rd = "R" + binaryToDec('0' + command.substring(16, 21));
                        output = instuction + " " + rd + ", " + rs + ", " + rt + line_break;
                    }

                }
            } else {
                String tempoutput = binaryToDec(command);
                output = tempoutput + line_break;
                dataList.add(tempoutput);
            }
            outputcommand.add(output);
        }

        File disassembly_file = new File("disassembly_stu.txt");
        if (!disassembly_file.exists() || !disassembly_file.isFile()) {
            disassembly_file.createNewFile();
        } else {
            disassembly_file.delete();
            disassembly_file.createNewFile();
        }

        BufferedWriter outScream =
                new BufferedWriter(new FileWriter(disassembly_file));

        Map<String, String> instuctionmap = new HashMap<String, String>();

        for (int i = 0; i < inputcommand.size(); i++) {
            String address = Integer.toString(256 + 4 * i);
            outScream.append(inputcommand.get(i) + "\t" + address + "\t" + outputcommand.get(i));
            instuctionmap.put(address, outputcommand.get(i));
        }
        outScream.close();

        //disassembly.txt has finished
        boolean isfirst = false;
        if (isfirst) {
            int cycle = 0;
            boolean hasfinished = false;
            int now_address = 256;
            int[] register = new int[32];

            File simulation_file = new File("simulation_stu.txt");
            if (!simulation_file.exists() || !simulation_file.isFile()) {
                simulation_file.createNewFile();
            } else {
                simulation_file.delete();
                simulation_file.createNewFile();
            }

            outScream = new BufferedWriter(new FileWriter(simulation_file));
            while (!hasfinished) {
                cycle = cycle + 1;
                int new_now_address = execute_instrucion(now_address, register, dataList, 256 + 4 * breakcycle,
                        instuctionmap, hasfinished);
                if (new_now_address < 0) {
                    hasfinished = true;
                }

                for (int i = 0; i < 20; i++) {
                    outScream.append('-');
                }
                outScream.append(line_break);

                outScream.append("Cycle:" + cycle + "\t" + now_address + "\t"
                        + instuctionmap.get(Integer.toString(now_address).replaceAll(" ", "\t")));

                outScream.append(line_break);
                printRegisterandData(outScream, line_break, register, breakcycle, dataList);
                now_address = new_now_address;
            }
            outScream.close();
        } else {
            //start the pipeline execute
            int cycle = 0;
            boolean hasfinished = false;
            boolean fetchhasfinished = false;
            int now_address = 256;
            int[] register = new int[32];
            int[] newregister = new int[32];
            int[] registerinuse = new int[32];
            List<String> newdataList = new LinkedList<String>();
            RenewList(newdataList, dataList);
            int[] IF_Unit = {0, 0};
            List<Integer> Pre_Issue_Queue = new LinkedList<Integer>();
            List<Integer> Pre_ALU1_Queue = new LinkedList<Integer>();
            List<Integer> Pre_Mem_Queue = new LinkedList<Integer>();
            List<Integer> Post_Mem_Queue = new LinkedList<Integer>();
            List<Integer> Pre_ALU2_Queue = new LinkedList<Integer>();
            List<Integer> Post_ALU2_Queue = new LinkedList<Integer>();
            Map<String, List<Integer>> Queue_Map = new HashMap<String, List<Integer>>();
            Queue_Map.put("Pre_Issue_Queue", Pre_Issue_Queue);
            Queue_Map.put("Pre_ALU1_Queue", Pre_ALU1_Queue);
            Queue_Map.put("Pre_Mem_Queue", Pre_Mem_Queue);
            Queue_Map.put("Post_Mem_Queue", Post_Mem_Queue);
            Queue_Map.put("Pre_ALU2_Queue", Pre_ALU2_Queue);
            Queue_Map.put("Post_ALU2_Queue", Post_ALU2_Queue);

            File simulation_file = new File("simulation_stu.txt");
            if (!simulation_file.exists() || !simulation_file.isFile()) {
                simulation_file.createNewFile();
            } else {
                simulation_file.delete();
                simulation_file.createNewFile();
            }

            outScream = new BufferedWriter(new FileWriter(simulation_file));

            int pipelineaddress = now_address;
            boolean[] haspipelineover = {false};
            while (!haspipelineover[0]) {
                cycle = cycle + 1;
                pipelineaddress = excutePipeline(now_address, register, dataList, breakcycle,
                        instuctionmap, haspipelineover, IF_Unit, Queue_Map, pipelineaddress, fetchhasfinished, registerinuse);

                for (int i = 0; i < 20; i++) {
                    outScream.append('-');
                }
                outScream.append(line_break);

                outScream.append("Cycle:" + cycle + line_break);
                outScream.append(line_break);

                outScream.append("IF Unit:" + line_break);
                outScream.append("\tWaiting Instruction:");
                if (IF_Unit[0] > 0) {
                    outScream.append(" [" + instuctionmap.get(Integer.toString(IF_Unit[0])).replaceAll(" ", "\t").replaceAll(line_break, "") + " ]");
                }
                outScream.append(line_break);

                outScream.append("\tExecuted Instruction:");
                if (IF_Unit[1] > 0) {
                    outScream.append(" [" + instuctionmap.get(Integer.toString(IF_Unit[1])).replaceAll(" ", "\t").replaceAll(line_break, "") + " ]");
                }
                outScream.append(line_break);

                outScream.append("Pre-Issue Queue:");
                outScream.append(line_break);
                for (int i = 0; i < 4; i++) {
                    outScream.append("\tEntry " + i + ": ");
                    if (i < Queue_Map.get("Pre_Issue_Queue").size()) {
                        outScream.append(" [" + instuctionmap.get(Integer.toString(Queue_Map.get("Pre_Issue_Queue").get(i))).replaceAll(" ", "\t").replaceAll(line_break, "") + " ]");
                    }
                    outScream.append(line_break);
                }

                outScream.append("Pre-ALU1 Queue:");
                outScream.append(line_break);
                for (int i = 0; i < 2; i++) {
                    outScream.append("\tEntry " + i + ": ");
                    if (i < Queue_Map.get("Pre_ALU1_Queue").size()) {
                        outScream.append(" [" + instuctionmap.get(Integer.toString(Queue_Map.get("Pre_ALU1_Queue").get(i))).replaceAll(" ", "\t").replaceAll(line_break, "") + " ]");
                    }
                    outScream.append(line_break);
                }
                outScream.append("Pre-MEM Queue:");
                for (int i = 0; i < 1; i++) {
                    if (i < Queue_Map.get("Pre_Mem_Queue").size()) {
                        outScream.append(" [" + instuctionmap.get(Integer.toString(Queue_Map.get("Pre_Mem_Queue").get(i))).replaceAll(" ", "\t").replaceAll(line_break, "") + " ]");
                    }
                    outScream.append(line_break);
                }

                outScream.append("Post-MEM Queue:");
                for (int i = 0; i < 1; i++) {
                    if (i < Queue_Map.get("Post_Mem_Queue").size()) {
                        outScream.append(" [" + instuctionmap.get(Integer.toString(Queue_Map.get("Post_Mem_Queue").get(i))).replaceAll(" ", "\t").replaceAll(line_break, "") + " ]");
                    }
                    outScream.append(line_break);
                }

                outScream.append("Pre-ALU2 Queue:");
                outScream.append(line_break);
                for (int i = 0; i < 2; i++) {
                    outScream.append("\tEntry " + i + ": ");
                    if (i < Queue_Map.get("Pre_ALU2_Queue").size()) {
                        outScream.append(" [" + instuctionmap.get(Integer.toString(Queue_Map.get("Pre_ALU2_Queue").get(i))).replaceAll(" ", "\t").replaceAll(line_break, "") + " ]");
                    }
                    outScream.append(line_break);
                }

                outScream.append("Post-ALU2 Queue:");
                for (int i = 0; i < 1; i++) {
                    if (i < Queue_Map.get("Post_ALU2_Queue").size()) {
                        outScream.append(" [" + instuctionmap.get(Integer.toString(Queue_Map.get("Post_ALU2_Queue").get(i))).replaceAll(" ", "\t").replaceAll(line_break, "") + " ]");
                    }
                    outScream.append(line_break);
                }
                outScream.append(line_break);

                printRegisterandData(outScream, line_break, newregister, breakcycle, newdataList);
                newregister = register.clone();
                newdataList.clear();
                RenewList(newdataList, dataList);
            }
            outScream.close();
        }
    }
}
