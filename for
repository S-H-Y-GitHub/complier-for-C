FOR->for( ASS1 ;M11 BOOL ; M9 ASS2){ M10 A}


		ASS1
		
L3:		BOOL				//登记L3
		
		if BOOL goto L1		//待回填L1
		goto L2				//待回填L2
L4:		ASS2				//登记L4

		goto L3				//使用登记信息L3
L1:		A					//回填L1

		goto L4				//使用登记信息L4
L2:		...					//回填L2