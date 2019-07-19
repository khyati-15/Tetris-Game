import essentials.*;
import java.util.Scanner;
import java.util.*;

class Tetris
{
    public static void clearscreen()
    {
        try{
            new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
        }
        catch(Exception e)
        {
            
        }
    }
    public static void main(String []args)
    {
        Board b=new Board();
        b.generate_board();
        int fix=0;
        
        Undo u[]=new Undo[10];
        Undo r[]=new Undo[10];
        
        char [][]mapped =new char[][]{{'l','r'},{'r','l'},{'d','u'},{'c','a'},{'a','c'}};
        for(int i=0;i<10;i++)
        {
            u[i]=new Undo();
            r[i]=new Undo();
        }
        int undosize=0,redosize=0;
        while(true){
        
            if(fix>100)
                break;
        Shapes s=new Shapes();
        int coords[][]=s.generate_Shape();
        b.setBoard(coords);
            clearscreen();
        b.display_board();
    
        Scanner sc=new Scanner(System.in);
        char ch;
        
        while(true){
            ch=sc.next().charAt(0);
            int br=0;
            for(int i=0;i<4;i++)
            {
                if(s.coords[i][0]+1==19)
                {
                    br=1;
                    break;
                }
            }
            if(br==1){
                  for(int i=0;i<coords.length;i++)
                { 
                    fix++;
                    b.count[coords[i][0]]++;
                }
                b.findandremove();
                break;
            }
                int newcoords[][]=new int[4][2];
        for(int i=0;i<4;i++)
        {
            newcoords[i][0]=s.coords[i][0];
            newcoords[i][1]=s.coords[i][1];
        }
        if(ch=='l' || ch=='L')
            coords=s.moveleft(b.screen);
        else if(ch=='d' ||  ch=='D')
            coords=s.movedown(b.screen);
        else if(ch=='r' ||  ch=='R')
            coords=s.moveright(b.screen);
        else if(ch=='a' || ch=='A')
            coords=s.rotateleft(b.screen);
        else if(ch=='c' || ch=='C')
            coords=s.rotateright(b.screen);
        else if(ch=='u' || ch=='U')
        {
            if(undosize!=0)
            {
                for(int i=0;i<5;i++)
                {
                    if(u[undosize-1].operation==mapped[i][0])
                    {
                       
                        if(mapped[i][1]=='l')
                            coords=s.moveleft(b.screen);
                        else if(mapped[i][1]=='u')
                            coords=s.moveup(b.screen);
                        else if(mapped[i][1]=='r')
                            coords=s.moveright(b.screen);
                        else if(mapped[i][1]=='a')
                            coords=s.rotateleft(b.screen);
                        else if(mapped[i][1]=='c')
                            coords=s.rotateright(b.screen);
                        else if(mapped[i][1]=='d')
                            coords=s.movedown(b.screen);
                        for(int j=0;j<undosize-1;j++)
                            u[j].operation=u[j+1].operation;
                        r[redosize].operation=u[undosize-1].operation;
                        redosize++;
                        undosize--;
                        break;
                    }
                }
            }
        }
    else if(ch=='o' || ch=='O')
        {
            if(redosize!=0)
            {
                for(int i=0;i<5;i++)
                {
                    if(r[redosize-1].operation==mapped[i][0])
                    {
                       
                        if(mapped[i][0]=='l')
                            coords=s.moveleft(b.screen);
                        else if(mapped[i][0]=='u')
                            coords=s.moveup(b.screen);
                        else if(mapped[i][0]=='r')
                            coords=s.moveright(b.screen);
                        else if(mapped[i][0]=='a')
                            coords=s.rotateleft(b.screen);
                        else if(mapped[i][0]=='c')
                            coords=s.rotateright(b.screen);
                        else if(mapped[i][0]=='d')
                            coords=s.movedown(b.screen);
                        for(int j=0;j<redosize-1;j++)
                            r[j].operation=r[j+1].operation;
                        u[undosize].operation=r[redosize-1].operation;
                        undosize++;
                        redosize--;
                        break;
                    }
                }
            }
        }
        else{
            ch='d';
            coords=s.movedown(b.screen);
        }
            
            int c=0;
            for(int i=0;i<4;i++)
            {
                if(coords[i][0]==newcoords[i][0] && coords[i][1]==newcoords[i][1])
                    c++;
            }
            
            b.setBoard(coords);
            clearscreen();
            b.display_board();
            if(c!=4 && ch!='u')
            {
                u[undosize].operation=ch;
                undosize++;
            }
            if(undosize==10)
            {
                undosize=0;
		redosize=0;
            }
            if(c==4 && s.flag==1 && ch!='u'){
                undosize=0;
                 for(int i=0;i<coords.length;i++)
                { 
                    fix++;
                    b.count[coords[i][0]]++;
                }
                b.findandremove();
                break;
            }
        }
        }
        System.out.println("GAME OVER");
    }
}