/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package linux_cmdsimulator;

/**
 *
 * @author Soha Samad
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;

public class Linux_cmdSimulator {

     public static void writeToFile(String filename,String []values) //function to write to a file.
    {
             try{
                 FileWriter fr=new FileWriter(System.getProperty("user.dir")+"\\"+filename);
                   BufferedWriter out=new BufferedWriter(fr);
                 for(String value:values)
                 {out.write(value);out.newLine();}
                 
                 out.close();
             }
             catch (FileNotFoundException ex) {
                    System.out.print("File not found!");
           } catch (IOException ex) {
                    System.out.println("Input/Output exception!");
                }
                 
    }
    
    public static void writeToFile2(String filename,String[] values) //function to write/append to a file.
    {
             try{
                   FileWriter fr=new FileWriter(System.getProperty("user.dir")+"\\"+filename,true);
                   PrintWriter out=new PrintWriter(fr,true);
                   for(String value : values)
                   {out.append(value); out.println();}
                   out.close();
             }
             catch (FileNotFoundException ex) {
                    System.out.println("File not found!");
                } catch (IOException ex) {
                    System.out.println("Input/Output exception!");
                }
                 
    }
    public static void clear(){
    //Clears Screen in java
    try {
        if (System.getProperty("os.name").contains("Windows"))
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        else
            Runtime.getRuntime().exec("clear");
    } catch (IOException | InterruptedException ex) {}
}
    
    public static void pwd()
    {
        String pwd=System.getProperty("user.dir");
        System.out.println(pwd);
    }
    
    public static void ls()
    {
         File dir=new File(System.getProperty("user.dir"));
         String files[]=dir.list();
         for(String file : files)
         {
           System.out.println(file);
         }
    
    }
    public static void cd(String args)
    {
        String defaultDir;
        if(args.equals(" "))
        { 
            defaultDir="C:\\test";
            System.setProperty("user.dir",defaultDir);
        }
        
        else if(args.equals(".."))
        {
          File dir=new File(System.getProperty("user.dir"));
          String parentPath=dir.getParent();
          if(parentPath==null)
              System.out.println("This directory doesnt have a parent directory!");
         else
              System.setProperty("user.dir", parentPath);
        }
        
        else
        {
              File dir=new File(args);
            if(dir.isDirectory())
               System.setProperty("user.dir",dir.getAbsolutePath());
            else
               System.out.println("No such directory exists");     
        }
       
    
    }
    
    public static void Date() // for getting and setting date and time
    {       
           Date date=new Date();
           System.out.print(date.toString());
      
    }
            public static void rmdir(String arg) //rmdir takes dir as an argument.
            {
                File dir=new File(arg);
                if(dir.isDirectory())
                {
                  String [] children=dir.list(); 
                  if(children.length!=0)// if this directory has children, issue an error
                   System.out.print("Error : the directory isnt empty !");
                  else // if this directory is empty then delete it !
                  { dir.delete(); System.out.println("Directory deleted successfully");
                }
            
            }
            }
            public static void mkdir(String arg) //takes the full path to make a dir.
            {
                File dir=new File(arg);
              if(!dir.isDirectory()){
                   boolean success=dir.mkdir();
                   if(success==true)
                   System.out.println("directory created!");
                   else
                       System.out.println("directorry creation failed!");
              }
              else
                  System.out.println("This directory already exists!");
            }
            
            public static void deletedir(File dir)
            {
                   File[] files = dir.listFiles();
               
                   for (File myFile: files) 
                   {
                      if (myFile.isDirectory()) {  
                          deletedir(myFile);
                      } 
                       myFile.delete();

                   }
                
            
            }
               
            
            
            public static void rm (String arg) //rm delete a file.txt(in the dir iam in rn.) or recursivly delete directories and it's contents.
            {
           
                 if(arg.contains("-r")) // then the given arg is a diretory.
                 {
                     File Myfile=new File(arg.substring(3));
                     if(Myfile.isDirectory())
                     {
                         deletedir(Myfile); //sending the directory to be deleted.
                         System.out.println("directory and it's contents deleted successfully !");
                     }
                     else
                         System.out.println("This is not a directory! "+arg.substring(3));
                 }
                 
                 else
                 {
                    File Myfile=new File(System.getProperty("user.dir"),arg);// gets the directory iam on.
                    Myfile.delete();
                    System.out.println(arg+" File is deleted successfully");
                 }
            }
            
            public static void mv(String arg) // arg1:takes two files , arg2:takes 2 dir , arg3 : takes a file and a dir  
            { // you have to be in the same directory when moving.
                String []files=arg.split("\\s+");
                String path=System.getProperty("user.dir");
                if(files[0].contains(".txt") && files[1].contains(".txt")) //renames files in the same dir
                 {
                                File oldfile =new File(path+"\\"+files[1]);
                                File newfile =new File(path+"\\"+files[0]);

                            if(oldfile.renameTo(newfile))
                               System.out.println("File renamed");
                            else
                               System.out.println("Sorry! the file can't be renamed");
                 }
                else if(files[0].contains(".txt")) //moves a files from a directory to another
                {
                    File file=new File(files[1]);//dir plus name of the file.
                    File oldfile=new File(path); //the path im in plus file's name
                    
                    File[]fs=oldfile.listFiles();
                    for(File f:fs)
                    {
                        if(f.getName().equals(files[0]))
                        f.renameTo(new File(files[1]+"\\"+f.getName()));
                    }
                }
                
                else //renames directory , doesnt handle dir that has spaces.
                {
                   File oldDir=new File(files[1]);
                   File newDir=new File(files[0]);
                   if(oldDir.renameTo(newDir))
                               System.out.println("File renamed");
                            else
                               System.out.println("Sorry! the directory can't be renamed");
                }
                    
                
            }
            
       public static void cp(String arg) //copying a file ,arg: two files, first src file , sec: dest file
       { // FOR THE CP TO WORK THE FILES SHOULD BE IN THE PROJECT DIRECTORY MADE OR BE GIVEN THE FULL PATH AS ARGUMENT.
          String []files=arg.split("\\s+"); //splitting the arg by whitespaces.
          File dir=new File(files[0]);
          if(files[0].contains(".txt")&& files[1].contains(".txt"))
          {
              InputStream is=null;
              OutputStream os=null;
          try{
          is=new FileInputStream(files[0]); // source file
          os=new FileOutputStream(files[1]);//dest file.
          byte[] buffer=new byte[1024];
          int length;
          while((length=is.read(buffer))>0)
          {     os.write(buffer,0,length);   }
          System.out.println("Copying is done successfully");
          is.close();
          os.close();
          
              } catch (IOException ex) {
                  System.out.println("Exception: problem with opening file or Input/output!");
              }
          }
          
          else
              System.out.println("command or syntax error in cp function!");
       }
            
       public static void cat(String arg) //takes a file and displays it, or takes two files concat and display them.
       { // you have to be in the same dir o fthe files given.
            String []files=arg.split("\\s+");
            if(files.length==1)
            {
                try {
                    FileReader filereader=new FileReader(System.getProperty("user.dir")+"\\"+files[0]); //have to write the full path
                    BufferedReader in=new BufferedReader(filereader);
                    String line;
                    while((line=in.readLine())!=null)
                    {System.out.print(line);}
                } catch (FileNotFoundException ex) {
                    System.out.println("File not found!");
                } catch (IOException ex) {
                    System.out.println("Input/Output exception!");
                }
                
            }
            else 
            {
                int count=0;
                try{
                while(count<2)
                {
                   
                    FileReader filereader=new FileReader(System.getProperty("user.dir")+"\\"+files[count]);
                    BufferedReader in=new BufferedReader(filereader);
                    String line;
                    while((line=in.readLine())!=null)
                    {System.out.print(line);}
                
                   count++;
                
                }
                }
                catch (FileNotFoundException ex) {
                    System.out.print("File not found!");
                } catch (IOException ex) {
                    System.out.print("Input/Output exception!");
                }
            }
       System.out.println();
       }
       
       public static void redir(String arg) //create/replace redirecting ,arg carries the command
       { //you have to be in the same dir of the file to redirect to the file.
          
          String []cmd=arg.split("\\s+"); //splitting by whitespaces.
          if(arg.contains("ls"))
          {
             
                    File dir=new File(System.getProperty("user.dir"));
                   String files[]=dir.list();
                   //for(String file : files)
                      writeToFile(cmd[2],files);//arguments:filename,content to the file.
                   
              }
          else if(arg.contains("pwd"))
          {
               String[] pwd={System.getProperty("user.dir")};
                writeToFile(cmd[2],pwd);
          }
          else if(arg.contains("date"))
          {
             Date date=new Date();
             String[]d={date.toString()};
             writeToFile(cmd[2],d);
          }
          else
          {String[] val={cmd[0]};
          writeToFile(cmd[2],val);}
             
      
       }
       public static void redir2(String arg) //create/append redirecting.
       {// you have to be in the same dir of the file you are redirecting to.
          String []cmd=arg.split("\\s+"); 
          if(arg.contains("ls"))
          {
             
                    File dir=new File(System.getProperty("user.dir"));
                   String files[]=dir.list();
                      writeToFile2(cmd[2],files);//arguments:filename,content to the file.
                   
              }
          else if(arg.contains("pwd"))
          {
               String[] pwd={System.getProperty("user.dir")};
                writeToFile2(cmd[2],pwd);
          }
          else if(arg.contains("date"))
          {
             Date date=new Date();
             String[]d={date.toString()};
             writeToFile2(cmd[2],d);
          }
          else //writing word to file directly.
          {
              String[]val={cmd[0]};
             writeToFile2(cmd[2],val);
          }
       
       }
               
        
       
        public static void help()
        {
        System.out.println("cd: This command changes the current directory to another one.  ");
        System.out.println("clear: Clears the console ");
        System.out.println("ls: lists files and directories the the current directory ");
        System.out.println("pwd: Display current user directory");
        System.out.println("cp: Copys the contents of a file into another file");
        System.out.println("mv: Renames files or directories or moves files to another specified directory");
        System.out.println("rm: Removes one file(you have to be in it's dir) or a directory");
        System.out.println("mkdir: makes/creates a new directory");
        System.out.println("rmdir: Removes a specific directory ");
        System.out.println("cat: Displays the contents of a file to the terminal/cmd");
        System.out.println("args: list all parameters on the command line, numbers or strings for specific command");
        System.out.println("date: Current date and time");
        System.out.println("help: list all user commands and the syntax of their arguments");
        System.out.println("Exit: exits from the cmd");
        }
        public static void help_spec(String x)        
        {
           if(x.equals("ls")) System.out.println("ls: lists files and directories in the current directory ");
           else if(x.equals("pwd")) System.out.println("pwd: Display current user directory");
           else if(x.equals("cp")) System.out.println("cp: Copys the contents of a file into another file");
           else if(x.equals("cat"))  System.out.println("cat: Displays the contents of a file to the terminal/cmd");       
           else if(x.equals("rmdir")) System.out.println("rmdir: Removes a specific directory ");         
           else if(x.equals("mkdir")) System.out.println("mkdir: makes/creates a new directory");        
           else if(x.equals("rm")) System.out.println("rm: Removes one file(you have to be in it's dir) or a directory");
           else if (x.equals("mv")) System.out.println("mv: Renames files or directories or moves files to another specified directory");
           else if(x.equals("date")) System.out.println("date: Current date and time");
           else if(x.equals("help")) System.out.println("help: list all user commands and the syntax of their arguments");
        }
                
        public static void Args_help(String x)
        {
           if(x.equals("cd")){System.out.println("Doesnt take arguments->Return to the default directory");
           System.out.println("takes one argument(The directory)");}
           else if (x.equals("pwd")){System.out.println("Doesnt take any args");}
           else if(x.equals("cp")){System.out.println("Takes the full path of the two files , one is the src ,the othe ris the dist");} 
           else if(x.equals("cat")){System.out.println("Takes file name and displays it , or takes two files concat the and display them.");}
           else if(x.equals("rmdir")){System.out.println("Takes a dir as an argument");}
           else if(x.equals("mkdir")){System.out.println("Takes a dir as an argument");}
           else if(x.equals("rm")){System.out.println("Takes a dir or a file.txt ");}
           else if(x.equals("mv")){System.out.println("Takes 2 files or 2 directries or a file and a directory");}
           else if(x.equals("ls")){System.out.println("Doesnt take any arguments");}
           else if (x.equals("more")){System.out.print("Takes a filename as an argument");}
           else {System.out.println("No such command exist.");}
        
        }
        
        public static void more(String arg) // argument is a file name , you have to be in the same directory.
        {
             InputStream fstream=null;
           try{
    
          fstream=new FileInputStream(System.getProperty("user.dir")+"\\"+arg); // source file
         byte[] data=new byte[48]; 
        
          while((fstream.read(data))!=-1)
          {    String filecontent=new String (data);
              System.out.println(filecontent);  
              String c;  
              Scanner sc=new Scanner(System.in);
              c=sc.nextLine();}
                fstream.close();       
                } catch (FileNotFoundException ex) {
                    System.out.println("File not found!");
                } catch (IOException ex) {
                    System.out.println("Input/Output exception!");
                }
                 
        }
        
        
        
        
        public static void Conditions(String y)
        {
            try{
            if(y.contains("cd"))
            {   if(y.length()>2)
                    cd(y.substring(3));
                else
                    cd(" ");
            } 
                 
       else if (y.equals("clear"))
           clear();
     
       
        else if (y.equals("pwd"))
            pwd();
        else if(y.equals("ls"))
            ls();
        else if(y.equals("help")){help();}
        
        else if(y.equals("exit")){System.exit(0);}
        
        else if(y.equals("date")) {Date();}
               

        else if(y.contains("rmdir ")) // deletes a certain directory if it IS EMPTY.
        {
           rmdir(y.substring(6));
        }
       
        else if(y.contains("mkdir ")) //creates a directory.
        {
          mkdir(y.substring(6));
        }
        
        else if(y.contains("rm "))// handles: rm delete a file.txt or recursivly delete directories and it's contents.
        {
            rm(y.substring(3));        
        }
       else if (y.contains("mv ")) //handles: renames the file or a directory or moves a file from a directory to another one
       {
             mv(y.substring(3));
       }
        
       else if (y.contains("cp ")) //handles:taking and copying files only
       {
          cp(y.substring(3));
       }
       else if(y.contains("cat "))
       {
         cat(y.substring(4));
       }
       
       else if(y.contains(" > ")){redir(y);}
       else if(y.contains(">>")){redir2(y);}
       else if(y.contains("? ")){help_spec(y.substring(2));} // ex: ? ls  
       else if(y.contains("args ")){Args_help(y.substring(5));}   
       else if(y.contains("more ")){more(y.substring(5));}   
       else{System.out.println("Command does not exist");}
            
            
         }
            catch(Exception e)
                    {System.out.println("Error: command syntax");}
            
            
        }
        
        
    
    
   
    public static void main(String[] args) {
        
         while(true)
        {
        String x;
        Scanner sc= new Scanner(System.in);
        x=sc.nextLine();
        String[] cmd = null;
        if(x.contains(" | "))
        {
            cmd=x.split("[\\s][|][\\s]"); // spliliting the string by pipes.              
            for(int i=0;i<cmd.length;i++)
                Conditions(cmd[i]);
                
        }
        else
            Conditions(x);
       
             
       }
        
        
        
        
    }
    
}
