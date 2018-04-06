package edu.aakash.lintool3;

import edu.gwu.lintool.*;

    public class BombearComplex extends ComplexNumber 
	{
	
		
		//Constructor to initialize complex number
		public BombearComplex(double real_part, double img_part)
		{
			re=real_part;
			im=img_part;
			
			
		}
		public BombearComplex(ComplexNumber u)
		{
			re=u.re;
			im=u.im;
		}
		public BombearComplex()
		{
			re=0;
			im=0;
		}
		
		public double magnitude()
		{
			
			return java.lang.Math.sqrt((re*re)+(im*im));
		}
		
		public double angle()
		{
			
			double ang=Math.atan2(im,re);
			if(ang<0)
			{
				ang+=2*Math.PI;
			}
			else if(ang>2*Math.PI)
			{
				ang-=2*Math.PI;
			}
			return ang;
		}
		

		
		public ComplexNumber add (ComplexNumber b)
		{
        BombearComplex c = (BombearComplex) b;
        return new BombearComplex (re + c.re, im + c.im);
        
		}
		
		public ComplexNumber sub(ComplexNumber b)
		{
			BombearComplex c = (BombearComplex) b;
			return new BombearComplex(re-c.re,im-c.im);
		}
		
		public ComplexNumber mult(ComplexNumber b)
		{
			BombearComplex c = (BombearComplex) b;
			return new BombearComplex(re*c.re-im*c.im, re*c.im+im*c.re);
		}
		
		public ComplexNumber mult(double a)
		{
			return new BombearComplex(re*a,im*a);
		}
		
		public ComplexNumber pow(int n)
		{
			//double r=Math.pow(magnitude(),n);
			//double t=n*Math.toDegrees((angle()));
			//double real =r*(Math.cos(t));
			//double imagin=r*(Math.sin(t));
			
			//return new BombearComplex(real,imagin);
			
			BombearComplex a = new BombearComplex(this.re,this.im); 


        for(int j = 2; j <= n; j++) 
		{

            a = (BombearComplex) a.mult(this);
        }

        return a;
		}
			
		
		
		public ComplexNumber conjugate()
		{
			return new BombearComplex(re,-im);
		}
		
		public ComplexNumber[] add (ComplexNumber[] u,ComplexNumber[] v)
		{
			BombearComplex[] r= new BombearComplex[u.length];
			BombearComplex obj = new BombearComplex();
			if(u!=null && v!=null && (u.length==v.length))
			{	
				for(int i=0;i<u.length;i++)
				{
					r[i]= new BombearComplex();
				}
				for(int i =0; i<u.length;i++)
				{
					
					r[i] = (BombearComplex)obj.add(u[i],v[i]);
				}
				return r;
			}
			else
			{
				return null;
			}
		}
		
		public ComplexNumber add(ComplexNumber u, ComplexNumber v)
		{
			BombearComplex a = (BombearComplex)u;
			BombearComplex b = (BombearComplex)v;
			return new BombearComplex(a.re+b.re, a.im+b.im);
		}
		
		public ComplexNumber ScalarMult(double alpha, ComplexNumber A)
		{
			return new BombearComplex(alpha*(A.re),alpha*(A.im));
		}
		
		public ComplexNumber[][] ScalarMatrixMult(double alpha, ComplexNumber[][] A)
		{
			if(A==null)
			{
				return null;
			}
			else
			{
				BombearComplex [][] res = new BombearComplex[A.length][A[0].length];
				for(int i=0;i<A.length;i++)
				{
					for(int j=0;j<A[0].length;j++)
					{
						res[i][j]=(BombearComplex)ScalarMult(alpha, A[i][j]);
					}
				}
				return res;
			}
		}
		
		public ComplexNumber[][] addMatrix (ComplexNumber[][] A, ComplexNumber[][] B)
		{
			if(A!=null || B!= null)
			{
				BombearComplex [][] res = new BombearComplex[A.length][A[0].length];
				for(int i=0;i<A.length;i++)
				{
					for(int j=0;j<A[0].length;j++)
					{
						BombearComplex tempA=(BombearComplex)A[i][j];
						BombearComplex tempB=(BombearComplex)B[i][j];
						res[i][j]= (BombearComplex)add(tempA,tempB);
					}
				}	
				return res;
			}
			else
			{
				return null;
			}
		}
		
		public ComplexNumber mult(ComplexNumber A, ComplexNumber B)
		{
			BombearComplex a = (BombearComplex)A;
			BombearComplex b = (BombearComplex)B;
			return new BombearComplex(b.re*a.re-b.im*a.im, b.re*a.im+b.im*a.re);
		}
		
		public ComplexNumber[][] ComplexMatMult(ComplexNumber[][] A, ComplexNumber[][] B)
		{
			if (A == null) 
			{
            return null;
			} 
			else if (B == null) 
			{
			return null;
			} 
			else if (A[0].length != B.length) 
			{
            return null;
			} 
			else 
			{
				BombearComplex [][] mult = new BombearComplex[A.length][B[0].length];
				BombearComplex prod =  new BombearComplex();
				for (int i = 0; i < A.length; i++) 
				{ 
					for (int j = 0; j < B[0].length; j++) 
					{	 
						for (int k = 0; k < A[0].length; k++) 
						{
                        prod = (BombearComplex)mult(A[i][k], B[k][j]);
						mult[i][j] = (BombearComplex)add(mult[i][j],prod);
						}
					}
				}

				return mult;
			}
		}
		
		public ComplexNumber[] ComplexMatVMult(ComplexNumber[][] A,ComplexNumber[] v)
		{
			if(A==null||v==null)
			{
				return null;
			}
			else if (A[0].length != v.length) 
			{
				return null;
			}
			else
			{
			BombearComplex[] c=new BombearComplex[A.length];
			
			for (int i = 0; i < A.length; i++)
			{
				BombearComplex sum = new BombearComplex();
				for (int j = 0; j < A[0].length; j++)
				{
					sum = (BombearComplex)add(sum,(BombearComplex)mult( A[i][j] , v[j]));
				}
				c[i]=sum;
			}
			return c;
			}
			
		}
		
		public ComplexNumber conjugate(ComplexNumber A)
		{
			return new BombearComplex(A.re,-A.im);
		}
		
		public ComplexNumber[][] HamTrans(ComplexNumber[][] A)
		{
			if(A==null)
			{
				return null;
			}
			else
			{
				BombearComplex [][] res=new BombearComplex[A[0].length][A.length];
			for (int i = 0; i < res.length; i++)
			{
				for (int j = 0; j < res[0].length; j++)
				{
					res[i][j] = (BombearComplex)A[j][i];
				}
			}
			for (int i = 0; i < res.length; i++)
			{
				for (int j = 0; j < res[0].length; j++)
				{
					res[i][j] = (BombearComplex)conjugate(res[i][j]);
				}
			}
			return res;
			}
		}
    }