import java.util.HashMap;

public class DES_Encryption {
	
    private String plaintext, key, ciphertext;
    
    final int[] IP = {
			58, 50, 42, 34, 26, 18, 10, 2,
            60, 52, 44, 36, 28, 20, 12, 4,
            62, 54, 46, 38, 30, 22, 14, 6,
            64, 56, 48, 40, 32, 24, 16, 8,
            57, 49, 41, 33, 25, 17, 9, 1,
            59, 51, 43, 35, 27, 19, 11, 3,
            61, 53, 45, 37, 29, 21, 13, 5,
            63, 55, 47, 39, 31, 23, 15, 7
	};
    
    final int[] E= {
			32, 1, 2, 3, 4, 5,
            4, 5, 6, 7, 8, 9,
            8, 9, 10, 11, 12, 13,
            12, 13, 14, 15, 16, 17,
            16, 17, 18, 19, 20, 21,
            20, 21, 22, 23, 24, 25,
            24, 25, 26, 27, 28, 29,
            28, 29, 30, 31, 32, 1
	};
    
    final int[] P= {
    		16, 7, 20, 21,
            29, 12, 28, 17,
            1, 15, 23, 26,
            5, 18, 31, 10,
            2, 8, 24, 14,
            32, 27, 3, 9,
            19, 13, 30, 6,
            22, 11, 4, 25
    };
    
    final int[] FP= {
    		40, 8, 48, 16, 56, 24, 64, 32,
            39, 7, 47, 15, 55, 23, 63, 31,
            38, 6, 46, 14, 54, 22, 62, 30,
            37, 5, 45, 13, 53, 21, 61, 29,
            36, 4, 44, 12, 52, 20, 60, 28,
            35, 3, 43, 11, 51, 19, 59, 27,
            34, 2, 42, 10, 50, 18, 58, 26,
            33, 1, 41, 9, 49, 17, 57, 25
    };
    
    // S boxes 3 Dimensional (box,row,column)
    final int[][][] S_Box= {
    		//Box1
    		{
    			{14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
                {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
                {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
                {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}
    		},
    		//Box2
    		{
    			{15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
                {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
                {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
                {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}
    		},
    		//Box3
    		{
    			{10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
                {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
                {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
                {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}
    		},
    		//Box4
    		{
    			{7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
                {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
                {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
                {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}
    		},
    		//Box5
    		{
    			{2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
                {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
                {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
                {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}
    		},
    		//Box6
    		{
    			{12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
                {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
                {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
                {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}
    		},
    		//Box7
    		{
    			{4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
                {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
                {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
                {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}
    		},
    		//Box8
    		{
    			{13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
                {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
                {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
                {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}
    		}
    };
    
    public DES_Encryption(String plain, String K) {
    	this.plaintext=plain;
    	this.key=K;
    	this.ciphertext="";
    }
    
    public String Encrypt() {
    	//
    	//System.out.println(this.plaintext);
    	//
    	/* Tạo 16 sub keys */
    	int[][] SubKeys=Get16Subkeys();  //SubKeys[16][48];
    	
    	/* Chuyển plaintext sang nhị phân */
    	String binaryPlaintext=string2binary(plaintext);
    	//System.out.println(binaryPlaintext);
    	
    	/* Chuyển chuỗi thành các block 64bits */
    	int blockSize = 64;
    	int numBlocks = Math.ceilDiv(binaryPlaintext.length(), blockSize);
    	
    	int[][] blocks64bits=new int[numBlocks][blockSize];
    	for(int i=0; i<numBlocks; i++) {
    		String blockString = binaryPlaintext.substring(i * blockSize, Math.min((i + 1) * blockSize, binaryPlaintext.length()));
            blockString = String.format("%-" + blockSize + "s", blockString).replace(' ', '0'); // Thêm bit 0 nếu cần
    		for(int j=0; j<blockSize; j++) {
    			blocks64bits[i][j]=Integer.parseInt(String.valueOf(blockString.charAt(j)));
    			//System.out.print(blocks64bits[i][j]);
    		}
    		//System.out.println();
    	}
    	
    	/* Mã hóa từng block 64bits */
    	for(int n=0; n<numBlocks; n++) {
    		/* Hoán vị khởi tạo IP */
    		int[] M=blocks64bits[n];
    		
    		int[] PM=new int[64];
    		for(int i=0; i<IP.length; i++) {
    			PM[i]=M[ IP[i]-1 ];
    		}
    		
    		/* Chia khối thành 2 nửa trái (L), phải (R) */
    		int[][] L = new int[18][32];
            int[][] R = new int[18][32];
            for (int i = 0; i < 32; i++) {
                L[0][i] = PM[i];
            }
            for (int i = 32; i < 64; i++) {
                R[0][i - 32] = PM[i];
            }
            
            /* Bắt đầu mã hóa (16 vòng) */
            for(int round=0; round<16; ++round) {
            	
            	//L(i+1)=R(i)
            	for (int i = 0; i < 32; i++) {
                    L[round + 1][i] = R[round][i];
                }
            	//mở rộng phần R(i)
            	
            	int[] ER = new int[48];
                for (int i = 0; i < E.length; i++) {
                    ER[i] = R[round][E[i] - 1];
                }
                //Thực hiện phép toán logic ER xor SubKeys[i]
                String KER = "";
                for (int i = 0; i < 48; i++) {
                    KER += ER[i] ^ SubKeys[round][i];
                }
                //Đưa chuỗi KER qua S-Box
                String sBoxValues = processSBox(KER);
                
                //Đưa chuỗi từ S-Box qua mảng hoán vị P
                
                
            	int[] f=new int[32];
            	for (int i = 0; i < P.length; i++) {
                    f[i] = Integer.parseInt(String.valueOf(sBoxValues.charAt( P[i]-1 )));
                }
            	
            	//Thực hiện phép toán logic L(i)[] xor f[] để tạo R(i+1)
            	for (int i = 0; i < 32; i++) {
                    R[round + 1][i] = L[round][i] ^ f[i];
                }
            }
            /* Hết 16 vòng */
            
            //Đổi chỗ R và L
            int[] RL = new int[64];
            for (int i = 0; i < 32; i++) {
            	//System.out.print(R[16][i] + " ");
                RL[i] = R[16][i];
            }
            for (int i = 0; i < 32; i++) {
                RL[i + 32] = L[16][i];
            }
            
            //Thực hiện hoán vị nghịch đảo FP
            
            String inverseIP = "";
            for (int i = 0; i < 64; i++) {
                inverseIP += RL[ FP[i] - 1 ];
            }
            
            //Đưa chuỗi inverseIP vào chuỗi ciphertext
            ciphertext+=inverseIP;
    	}
    	
    	//Chuyển ciphertext về chuỗi dạng ascii
    	ciphertext=binary2string(ciphertext);
    	//System.out.print(ciphertext);
    	return ciphertext;
    }
    
    private String processSBox(String binaryString) {
    	
        StringBuilder result = new StringBuilder();
        int index = 0;
        
        // Chia chuỗi nhị phân thành các khối 6 bit
        while (index < binaryString.length()) {
            String block = binaryString.substring(index, Math.min(index + 6, binaryString.length()));
            index += 6;
            
            // Lấy giá trị hàng và cột
            int row = Integer.parseInt(block.substring(0, 1) + block.substring(5, 6), 2);
            int col = Integer.parseInt(block.substring(1, 5), 2);
            
            // Lấy giá trị từ S-Box tương ứng
            int sBoxValue = S_Box[index / 6 - 1][row][col];
            
            // Chuyển giá trị từ S-Box thành chuỗi nhị phân 4 bit
            String sBoxResult = Integer.toBinaryString(sBoxValue);
            while (sBoxResult.length() < 4) {
                sBoxResult = "0" + sBoxResult;
            }
            
            // Nối kết quả vào chuỗi result
            result.append(sBoxResult);
        }
        return result.toString();
    }

	private int[][] Get16Subkeys() {
		
		/* String to Binary */
		String binaryKey=string2binary(key);
		int[] arrBinaryKey=new int[64];
		for(int i=0; i<64; i++) {
			if( i<binaryKey.length() ) {
				arrBinaryKey[i]=Integer.parseInt(String.valueOf(binaryKey.charAt(i)));
			}else {
				arrBinaryKey[i]=0;
			}
			//System.out.print(arrBinaryKey[i]);
		}
		//
		//System.out.println(binaryKey);
		//
		/* Hoán vị PC1 */
		final int[] PC1 = {
        		57, 49, 41, 33, 25, 17, 9,
                1, 58, 50, 42, 34, 26, 18,
                10, 2, 59, 51, 43, 35, 27,
                19, 11, 3, 60, 52, 44, 36,
                63, 55, 47, 39, 31, 23, 15,
                7, 62, 54, 46, 38, 30, 22,
                14, 6, 61, 53, 45, 37, 29,
                21, 13, 5, 28, 20, 12, 4
        };
		int[] PKey = new int[56];
		//System.out.println(PKey.length);
		for(int i=0; i<PKey.length; i++) {
			int position = PC1[i];
			PKey[i]=arrBinaryKey[position-1];
			//System.out.print(PKey[i]);
		}
		
		/* Tách key thành 2 nửa */
		int[] C0=new int[28];
		int[] D0=new int[28];
		for(int i=0; i<28; ++i) {
			C0[i]=PKey[i];
		}
		for(int i=28; i<56; ++i) {
			D0[i-28]=PKey[i];
		}
		//Dịch bit shift left
		int[][] C=new int[16][28];
		int[][] D=new int[16][28];
		for(int i=0; i<16; i++) {
			if(i==0 || i==1 || i==8 || i==15) {
				C0=shift_left(C0, 1);
				D0=shift_left(D0, 1);
			}else {
				C0=shift_left(C0, 2);
				D0=shift_left(D0, 2);
			}
			C[i]=C0;
			D[i]=D0;
		}
		
		/*Đưa C[], D[] qua PC2 để tạo mảng SubKey*/
		final int[] PC2 = {
				14, 17, 11, 24, 1, 5,
                3, 28, 15, 6, 21, 10,
                23, 19, 12, 4, 26, 8,
                16, 7, 27, 20, 13, 2,
                41, 52, 31, 37, 47, 55,
                30, 40, 51, 45, 33, 48,
                44, 49, 39, 56, 34, 53,
                46, 42, 50, 36, 29, 32
		};
		int[][] K=new int[16][48];
		for(int i=0; i<16; i++) {
			for(int j=0; j<PC2.length; j++) {
				if( PC2[j]<29 ) {
					K[i][j] = C[i][ PC2[j]-1 ];
				}else{
					K[i][j] = D[i][ PC2[j]-29 ];
				}
			}
		}
		
		return K;
	}

	private int[] shift_left(int[] data, int n) {
		
		int index=0;
		int[] ans=new int[28];
		for(int i=n; i<data.length; i++) {
			ans[index]=data[i];
		}
		for(int i=0; i<n; i++) {
			ans[index]=data[i];
		}
		return ans;
	}

	private String string2binary(String string) {
		
		String binaryString="";
		
		for(int i=0; i<string.length(); i++) {
			int asciiCode = string.charAt(i);
			String binaryChar = Integer.toBinaryString(asciiCode);
			while(binaryChar.length() < 8) {
				binaryChar = "0" + binaryChar;
			}
			binaryString+=binaryChar;
		}
		
		return binaryString;
	}
	
	private String binary2string(String binaryString) {
	    StringBuilder stringBuilder = new StringBuilder();
	    for (int i = 0; i < binaryString.length(); i += 8) {
	        String binaryChar = binaryString.substring(i, Math.min(i + 8, binaryString.length()));
	        int asciiCode = Integer.parseInt(binaryChar, 2);
	        stringBuilder.append((char) asciiCode);
	    }
	    return stringBuilder.toString();
	}
	
}
