import java.util.Scanner;
public class Checkers {
	public static String[][] board = new String [8][8];
	public static  final String white="W", red="R", validP="*", invalidP="-", queenR="Q-R", queenW="Q-W",
			stop="STOP";	
	public static  String whoseMove ;
	public static int Movefrom,Moveto,redcheckers,whitecheckers;
	static Scanner ch = new Scanner (System.in);
	public static void main(String[] args) {
		whoseMove=white;
		redcheckers=12;//מספר שחקני מחשב
		whitecheckers=12;//מזפר שחקני משתמש

		System.out.println("Welcome to Fatma Checkers. To start the game press 1 , to exit press 0 ");
		int choise= ch.nextInt();

		if(choise==1) {
			InitializeGame();//אתחול משחק
			PrintBoard();//הדפסת לוח
			do{
				UserTurn();//תור שחקן
				if(!EndGame()) {
					ComputerTurn();//תור מחשב
				}
			}while(!EndGame());
			GameResult();//תוצאת משחק
		}
		else {
			System.out.println("SeeAAAaA you");
		}				

	}//main
	public static void UserTurn() {
		boolean moved = false;
		while(!moved) {//כל עוד לא בוצע מהלך תקין
			System.out.println("It's your turn, please enter your move:");
			String playermove=ch.next();//קליטת מהלך

			if(CheckMove(playermove)) {//אם המהלך תקין ורגיל
				if((Moveto-Movefrom)==11||(Moveto-Movefrom)==9) { 
					Updatemove(Movefrom,Moveto);//עדכון לוח
					moved=true;
					break;
				}

				else {
					Updatemove(Movefrom,Moveto);//עדכון לוח
					while(AnotherEat(Movefrom)) {//כל עוד יש אכילה נוספת
						PrintBoard();
						System.out.println("You have another eat, please enter your move:");
						playermove=ch.next();
						if(Integer.parseInt(playermove.substring(0,2))==Movefrom&&CheckMove(playermove)) {

							Updatemove(Movefrom,Moveto);						
						}

						else System.out.println("Wrong move");
					}
					moved=true;//סיום תור
				}
			}
			else if(playermove.equals(stop)) {//המשתמש הכניס הכנעה
				whitecheckers=0;

				Movefrom=11;
				Moveto=11;
				break;
			}
			else 
				System.out.println("That was an invalid move, try again.");	

		}
		ifQueen(Movefrom);//המשתמש הגיע ךסוף הלוח
		whoseMove=red;//העברת תור
		PrintBoard();

	}
	public static void ComputerTurn() {
		boolean moved = false;
		while(!moved) {

			if(compeat()) {//יש למחשב אכילה וביצועה	
				while(AnotherEat(Movefrom)) {
					compeat(Movefrom);	//בדיקת אכילה נוספת		
				}
			}
			else {
				Randomove();//מהלך רנדומלי
			}

			moved=true;//סיום תור
		}
		ifQueen(Movefrom);
		System.out.println("Computer has played");
		PrintBoard();
		whoseMove=white;//העברת תור

	}
	public static boolean EndGame() {//בדיקת סיום משחק
		if(WinUser()||Tie()||WinCom()) {
			return true;
		}
		return false;
	}
	public static void GameResult() {//בדיקת תוצאת המשחק

		if(WinUser()) {//ניצחון משתמש
			System.out.println("Congratulations , user has won :)");
		}
		if(WinCom()) {//ניצחון מחשב
			System.out.println("Sorry,   computer has won :(");

		}
		if(Tie()) {//תיקו
			System.out.println("Well, unfortunately it's a Tie...");
		}

	}
	public static boolean Tie() {//בדיקת מצב תיקו
		if(Simplemove()||Haveeat()) {
			return false;
		}
		else
			return true;
	}
	public static boolean WinUser(){//בדיקה האם המשתנש ניצח
		if(redcheckers==0) {
			return true;
		}
		return false;
	}
	public static boolean WinCom() {//בדיקה האם המחשב ניצח
		if(whitecheckers==0) {
			return true;
		}
		return false;
	}
	public static void PrintBoard() {//הדפסת לוח המשחק
		System.out.println("The board:");
		for (int i = 0; i < board.length ; i++) {
			for (int j = 0; j < board[i].length ; j++) {
				System.out.print( board[i][j] + "\t");
			}
			System.out.println();
		}


	}
	public static void InitializeGame() {//אתחול לוח המשחק על פי החוקים
		for(int i=0 ;i< board.length;i++) {
			for(int j=0; j<board[i].length; j++) {
				if((i>=0 &&i<=2)) {
					if(i%2==0 && j%2==0) 
						board[i][j]=invalidP;
					else if(i%2!=0 && j%2!=0)
						board[i][j]=invalidP;
					else 
						board[i][j]=white;		
				}//if1

				if((i>=3 &&i<=4)) {
					if(i%2!=0 && j%2==0) 
						board[i][j]=validP;
					else if(i%2==0 && j%2!=0)
						board[i][j]=validP;
					else 
						board[i][j]=invalidP;
				}//if2

				if((i>=5 &&i<=7)){
					if(i%2==0 && j%2==0) 
						board[i][j]=invalidP;
					else if(i%2!=0 && j%2!=0)
						board[i][j]=invalidP;
					else 
						board[i][j]=red;
				}//if3
			}//for2
		}//for1
	}//InitializeGame
	public static boolean Randomove() {//מהלך רנדומלי של מחשב באמצעות סריקת הלוח
		for (int rf = 0; rf < board.length; rf++) {
			for (int cf = 0; cf < board[rf].length; cf++) {
				if (board[rf][cf] == red||board[rf][cf]==queenR) {//כלי אדום או מלכה
					for (int rt = 0; rt < board.length; rt++) {
						for (int ct = 0; ct < board[rt].length; ct++) {
							if(board[rt][ct]==validP) {
								if(board[rf][cf]==red) {//אם הכלי אדום בודק מקומות פנויים מסביב
									if ((cf-ct)==1&&(rt - rf == -1)){

										Updatemove((rf+1)*10+cf+1, (rt+1)*10+ct+1);//עדכון הלוח
										Movefrom=(rt+1)*10+ct+1;
										return true;
									}
									if((cf-ct)==-1&&(rt - rf == -1)){

										Updatemove((rf+1)*10+cf+1, (rt+1)*10+ct+1);
										Movefrom=(rt+1)*10+ct+1;
										return true;
									}
								}
								else if(board[rf][cf]==queenR) { //אם הכלי מלכה תבדור כל אפשרויות האכילה

									if((rf-rt)==(cf-ct)&&rf-rt<0) {
										for(int k=rf+1,f=cf+1;k<=ct;k++,f++) {
											if(board[k][f]==red||board[k][f]==queenR) {
												break;	
											}
											else {Updatemove((rf+1)*10+cf+1, (rt+1)*10+ct+1);
											Movefrom=(rt+1)*10+ct+1;
											return true;}
										}
									}
									else if((rf-rt)==-(cf-ct)) {
										for(int k=rf-1,f=cf+1;k<=ct;k--,f++) {
											if(board[k][f]==red||board[k][f]==queenR) {
												break;}
											else {Updatemove((rf+1)*10+cf+1, (rt+1)*10+ct+1);
											Movefrom=(rt+1)*10+ct+1;
											return true;}
										}

									}

									else if(-(rf-rt)==(cf-ct)) {
										for(int k=rf+1,f=cf-1;k<=ct;k++,f--) {
											if(board[k][f]==red||board[k][f]==queenR) {
												break;}
											else {Updatemove((rf+1)*10+cf+1, (rt+1)*10+ct+1);
											Movefrom=(rt+1)*10+ct+1;
											return true;}
										}
									}

									else if((rf-rt)==(cf-ct)&&rf-rt>0) {
										for(int k=rf-1,f=cf-1;k<=ct;k--,f--) {
											if(board[k][f]==red||board[k][f]==queenR) {
												break;}
											else {Updatemove((rf+1)*10+cf+1, (rt+1)*10+ct+1);
											Movefrom=(rt+1)*10+ct+1;
											return true;}
										}

									}
								}
							}

						}
					}
				}
			}
		}
		return false;//לא קיים מהלך תקין


	}
	public static boolean CheckMove(String Move) {//בדיקת תקינות מהלך
		if(Move.length()!=5) {//אורך המחזורת קטן מהערך הצפוי
			return false;
		}
		else if(Move.charAt(2)!='-') {//חסר תו"-"
			return false;
		}
		else {
			//סידור קוארדינטות תזוזה
			int MF = MoveFrom(Move);		
			int MT = MoveTo(Move);
			int rfrom = MF/10 - 1;
			int cfrom = MF%10 - 1;
			int rto = MT/10 - 1;
			int cto = MT%10 - 1;

			// בודק אם בטווח 
			if (rfrom < 0 || rfrom > 7 || cfrom < 0 || cfrom > 7 ||
					rto < 0 || rto > 7 || cto < 0 || cto > 7) 
				return false;

			// בודק אם זז לתא ריק
			else if (board[rfrom][cfrom]==whoseMove && board[rto][cto]==validP) {

				// מקרה של מהלך רגיל
				if (SimpleMoveUser(rfrom,cfrom,rto,cto)) {
					return true;}
				//מקרה של "אכילה" 
				else if (JumpUser(rfrom,cfrom,rto,cto)) {
					return true;
				}

			}
			//מהלך עם מלכה
			else if(board[rfrom][cfrom]==queenW&&QueenMove(rfrom, cfrom, rto, cto)) {
				return true;
			}
		}
		//מהלך לאת קין
		return false;
	}
	public static void ifQueen(int Movefrom) {//בודק האם כלי הופף למלכה 
		if(board[Movefrom/10-1][Movefrom%10-1]==white && Movefrom/10==8) {//המשתמש הגיע לסוף הלוח
			board[Movefrom/10-1][Movefrom%10+1]=queenW;			
		}
		else if(board[Movefrom/10-1][Movefrom%10-1]==red && Movefrom/10==1) {//המחשב הגיע לסוף הלוח
			board[Movefrom/10-1][Movefrom%10-1]=queenR;			
		}
	}
	public static boolean AnotherEat(int Movefrom) {//אכילות נוספות
		if(whoseMove==white){
			if((Movefrom/10+1>=0 && Movefrom/10+1<=7)&&(Movefrom%10+1>=0&&Movefrom%10+1<=7)
					&&board[Movefrom/10][Movefrom%10]==red
					&&board[Movefrom/10+1][Movefrom%10+1]==validP) {
				return true;
			}//קיימת אפשרות אכילה באלכסון הימני החתתון של הלבן
			else if((Movefrom/10+1>=0 && Movefrom/10+1<=7 )&&(Movefrom%10-3>=0&&Movefrom%10-3<=7)
					&&board[Movefrom/10][Movefrom%10-2]==red
					&&board[Movefrom/10+1][Movefrom%10-3]==validP) {
				return true;
			}//קיימת אפשרות אכילה באלכסון השמאלי  התחתון של הלבן
			else if((Movefrom/10-3>=0 &&Movefrom/10-3<=7)&&(Movefrom%10+3>=0 && Movefrom%10+3<=7)
					&&board[Movefrom/10-2][Movefrom%10+2]==red
					&&board[Movefrom/10-3][Movefrom%10+3]==validP) {
				return true;
			}//אלכסון ימני עליון של הלבן
			else if((Movefrom/10-3>=0 &&Movefrom/10-3<=7)&&(Movefrom%10-3>=0 &&Movefrom%10-3<=7)
					&&board[Movefrom/10-2][Movefrom%10-2]==red
					&&board[Movefrom/10-3][Movefrom%10-3]==validP){
				return true;
			}//אלכסון שמאלי עליון של לבן
			return false;		

		}//if white
		if(whoseMove==red){
			if((Movefrom/10+1>=0 && Movefrom/10+1<=7)&&(Movefrom%10+1>=0&&Movefrom%10+1<=7)
					&&board[Movefrom/10][Movefrom%10]==white
					&&board[Movefrom/10+1][Movefrom%10+1]==validP) {
				return true;
			}//קיימת אפשרות אכילה באלכסון הימני החתתון של האדום
			else if((Movefrom/10+1>=0 && Movefrom/10+1<=7 )&&(Movefrom%10-3>=0&&Movefrom%10-3<=7)
					&&board[Movefrom/10][Movefrom%10-2]==white
					&&board[Movefrom/10+1][Movefrom%10-3]==validP) {
				return true;
			}//קיימת אפשרות אכילה באלכסון השמאלי  התחתון של האדום
			else if((Movefrom/10-3>=0 &&Movefrom/10-3<=7)&&(Movefrom%10+1>=0 && Movefrom%10+1<=7)
					&&board[Movefrom/10-2][Movefrom%10]==white
					&&board[Movefrom/10-3][Movefrom%10+1]==validP) {
				return true;
			}//אלכסון ימני עליון של האדום
			else if((Movefrom/10-3>=0 &&Movefrom/10-3<=7)&&(Movefrom%10-3>=0 &&Movefrom%10-3<=7)
					&&board[Movefrom/10-2][Movefrom%10-2]==white
					&&board[Movefrom/10-3][Movefrom%10-3]==validP){
				return true;
			}//אלכסון שמאלי עליון של האדום
			return false;		

		}//if white
		return false;
	}
	public static int MoveFrom(String MF) {//המיקום ממנו המשתמש רוצה לבצע תזוזה
		Movefrom=Integer.parseInt(MF.substring(0,2));//שינוי הטיפוס הממחרוזת למספר ako
		return	Movefrom;
	}
	public static int MoveTo(String MT) {//המקום אליו המשתמש רוצה לבצע תזוזה
		Moveto=Integer.parseInt(MT.substring(3));//שינוי הטיפוס ממחרוזת למספר שלם
		return Moveto;
	}
	public static void Updatemove(int MF, int MT) {//עדכון המהלכים על פני לוח המשחק

		int rfrom = MF/10 - 1;
		int cfrom = MF%10 - 1;
		int rto = MT/10 - 1;
		int cto = MT%10 - 1;

		// Change appropriate board elements and decrement reds or whites if necessary.
		if(board[rfrom][cfrom]==queenW) {
			board[rfrom][cfrom] = "*";
			board[rto][cto] = queenW;
		}
		else if(board[rfrom][cfrom]==queenR) {
			board[rfrom][cfrom] = "*";
			board[rto][cto] = queenR;
		}
		else
			board[rfrom][cfrom] = "*";
		board[rto][cto] = whoseMove;

		if (Math.abs(rto - rfrom) == 2) {
			board[(rfrom+rto)/2][(cfrom+cto)/2] = "*";
			if (whoseMove.equals(white)||board[rto][cto]==queenW)
				redcheckers--;//הקטנת מספר האדומים במידה ונאכלו
			else
				whitecheckers--;//הקטנת מספר הלבנים במידה ונאכלו
		}
		else 
			if(board[rfrom][cfrom]==queenW) {
				UpdatequeenW(rfrom, cfrom, rto, cto);
			}
			else if(board[rfrom][cfrom]==queenR) {
				UpdatequeenR(rfrom, cfrom, rto, cto);
			}


		Movefrom=Moveto;
	}
	public static boolean compeat() {//בדיקת אפשרות האכילה של המחשב
		for (int rf = 0; rf < board.length; rf++) {
			for (int cf = 0; cf < board[rf].length; cf++) {
				if (board[rf][cf] == red||board[rf][cf] == queenR) {
					for (int rt = 0; rt < board.length; rt++) {
						for (int ct = 0; ct < board[rt].length; ct++) {
							if(board[rt][ct]==validP) {
								if (Math.abs(cf-ct)==2&&(rt - rf == -2)&&
										board[(rf+rt)/2][(cf+ct)/2] == white) {
									Updatemove((rf+1)*10+cf+1, (rt+1)*10+ct+1);
									Movefrom=(rt+1)*10+ct+1;
									return true;
								}

							}


						}
					}
				}
			}
		}
		return false;
	}
	public static boolean SimpleMoveUser(int Rf,int Cf,int Rt, int Ct ){//בדיקת אפשרות תזוזה במרחק של אחד עבור המשתמש
		if (Math.abs(Cf-Ct)==1) {
			if ((whoseMove == white) && (Rt - Rf == 1))
				return true;
		}
		return false;
	}
	public static boolean JumpUser(int Rf,int Cf,int Rt, int Ct) {// בדיקת אפשרות תזוזה של במרחק של 2 עבור המשתמש
		if (Math.abs(Cf-Ct)==2) {
			if (whoseMove == white && (Rt - Rf == 2) && 
					board[(Rf+Rt)/2][(Cf+Ct)/2] == red) {
				return true;
			}
		}
		return false;
	}
	public static boolean compeat(int MoveFrom) {	//בדיקת אפשרו אכילה של המחשב ממיקום מסויים		
		for (int rt = 0; rt < board.length; rt++) {
			for (int ct = 0; ct < board[rt].length; ct++) {					

				if(board[rt][ct]==validP //בדיקה האם הערך במרחק השווה לשתיים פנוי
						&&Math.abs(rt-(MoveFrom/10-1))==2
						&& (Math.abs(ct - (MoveFrom%10-1)) == 2)
						&&board[((MoveFrom/10-1)+rt)/2][((MoveFrom%10-1)+ct)/2] == white) {//האם קיים לבן במרחק אחד

					Updatemove(MoveFrom, (rt+1)*10+ct+1);
					Movefrom=(rt+1)*10+ct+1;
					return true;
				}
			}	
		}
		return false;
	}
	public static boolean Simplemove() {//בדיקת אפשרות למהלך שלא אכילה 
		for (int rf = 0; rf < board.length; rf++) {
			for (int cf = 0; cf < board[rf].length; cf++) {
				if (board[rf][cf] == red) {//עבור המחשב
					for (int rt = 0; rt < board.length; rt++) {
						for (int ct = 0; ct < board[rt].length; ct++) {
							if(board[rt][ct]==validP) {
								if ((cf-ct)==1&&(rt - rf == -1)){
									return true;
								}
								if((cf-ct)==-1&&(rt - rf == -1)){
									return true;
								}


							}

						}
					}
				}
				else if(board[rf][cf] == white) {//עבור המשתמש
					for (int rt = 0; rt < board.length; rt++) {
						for (int ct = 0; ct < board[rt].length; ct++) {
							if(board[rt][ct]==validP) {
								if ((cf-ct)==1&&(rt - rf == 1)){
									return true;
								}
								if((cf-ct)==-1&&(rt - rf == 1)){
									return true;
								}

							}

						}
					}
				}
			}
		}
		return false;
	}
	public static boolean Haveeat() {//בדיקה האם קיימת אכילה
		for (int rf = 0; rf < board.length; rf++) {
			for (int cf = 0; cf < board[rf].length; cf++) {
				if (board[rf][cf] == red) {//עבור המחשב
					for (int rt = 0; rt < board.length; rt++) {
						for (int ct = 0; ct < board[rt].length; ct++) {
							if(board[rt][ct]==validP) {//האם יעד התזוזה פנוי
								if (Math.abs(cf-ct)==2&&(rt - rf == -2)&&
										board[(rf+rt)/2][(cf+ct)/2] == white) {//האם בין המיקום הנוכחי לתזוזה יש חייל יריב

									return true;
								}

							}


						}
					}
				}
				else if (board[rf][cf] == white) {//עבור המשתמש
					for (int rt = 0; rt < board.length; rt++) {
						for (int ct = 0; ct < board[rt].length; ct++) {
							if(board[rt][ct]==validP) {
								if (Math.abs(cf-ct)==2&&(rt - rf == 2)&&
										board[(rf+rt)/2][(cf+ct)/2] == red) {

									return true;
								}

							}


						}
					}

				}
			}
		}
		return false;
	}
	public static boolean QueenMove(int RF,int CF,int RT,int CT) {//בדיקת אפשרויות התזוזה עבור המלכה
		if(Math.abs(RF-RT)!=Math.abs(CF-CT)) {
			return false;
		}
		else if((RF-RT)==(CF-CT)&&RF-RT<0) {
			for(int i=RF-1,j=CF-1;i<=CT;i++,j++) {
				if(board[i][j]==white||board[i][j]==queenW)
					return false;

			}
		}

		else if((RF-RT)==-(CF-CT)) {
			for(int i=RF+1,j=CF-1;i<=CT;i--,j++) {
				if(board[i][j]==white||board[i][j]==queenW)
					return false;

			}
		}

		else if(-(RF-RT)==(CF-CT)) {
			for(int i=RF-1,j=CF+1;i<=CT;i++,j--) {
				if(board[i][j]==white||board[i][j]==queenW)
					return false;

			}
		}

		else if((RF-RT)==(CF-CT)&&RF-RT>0) {
			for(int i=RF+1,j=CF+1;i<=CT;i--,j--) {
				if(board[i][j]==white||board[i][j]==queenW)
					return false;

			}

		}
		return true;
	}
	public static void UpdatequeenR(int RF,int CF,int RT,int CT) {//עדכון מהלך של מלכת המחשב
		if((RF-RT)==(CF-CT)&&RF-RT<0) {
			for(int i=RF+1,j=CF+1;i<=CT;i++,j++) {
				if(board[i][j]==white||board[i][j]==queenW) {
					board[i][j]=validP;
					whitecheckers--;//אכילת חיילים לבנים
				}

			}
		}

		else if((RF-RT)==-(CF-CT)) {
			for(int i=RF-1,j=CF+1;i<=CT;i--,j++) {
				if(board[i][j]==white||board[i][j]==queenW) {
					board[i][j]=validP;
					whitecheckers--;
				}

			}
		}

		else if(-(RF-RT)==(CF-CT)) {
			for(int i=RF+1,j=CF-1;i<=CT;i++,j--) {
				if(board[i][j]==white||board[i][j]==queenW) {
					board[i][j]=validP;
					whitecheckers--;
				}
			}
		}

		else if((RF-RT)==(CF-CT)&&RF-RT>0) {
			for(int i=RF-1,j=CF-1;i<=CT;i--,j--) {
				if(board[i][j]==white||board[i][j]==queenW) {
					board[i][j]=validP;
					whitecheckers--;
				}
			}
		}

	}
	public static void UpdatequeenW(int RF,int CF,int RT,int CT) {//עדכון מהלך של מלכת המשתמש
		if((RF-RT)==(CF-CT)&&RF-RT<0) {
			for(int i=RF+1,j=CF+1;i<=CT;i++,j++) {
				if(board[i][j]==red||board[i][j]==queenR) {
					board[i][j]=validP;
					redcheckers--;//הקטנת החיילים האדומים
				}
			}
		}

		else if((RF-RT)==-(CF-CT)) {
			for(int i=RF-1,j=CF+1;i<=CT;i--,j++) {
				if(board[i][j]==red||board[i][j]==queenR) {
					board[i][j]=validP;
					redcheckers--;
				}
			}
		}

		else if(-(RF-RT)==(CF-CT)) {
			for(int i=RF+1,j=CF-1;i<=CT;i++,j--) {
				if(board[i][j]==red||board[i][j]==queenR) {
					board[i][j]=validP;
					redcheckers--;
				}
			}
		}

		else if((RF-RT)==(CF-CT)&&RF-RT>0) {
			for(int i=RF-1,j=CF-1;i<=CT;i--,j--) {
				if(board[i][j]==red||board[i][j]==queenR) {
					board[i][j]=validP;
					redcheckers--;
				}


			}

		}
	}
}//class

