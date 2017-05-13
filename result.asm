.586
.model flat, stdcall
.stack 4096

option casemap : none

includelib msvcrt.lib

printf			PROTO C : ptr byte, : vararg
scanf			PROTO C : ptr byte, : vararg
gets				PROTO C : ptr byte
getchar		PROTO C
_getche		PROTO C
ExitProcess	PROTO, dwExitCode : DWORD; exit program


chr$ MACRO any_text : VARARG
LOCAL txtname
.data
IFDEF __UNICODE__
WSTR txtname, any_text
align 4
.code
EXITM <OFFSET txtname>
ENDIF
txtname db any_text, 0
align 4
.code
EXITM <OFFSET txtname>
ENDM

.data
	a	byte	?
	_v7	sdword	?
	b	sdword	9 dup(?)
	_v9	sdword	?
	i	sdword	?
	sum	sdword	?
	_b11	byte	?
	result	sdword	?
	_b5	byte	?
	_b8	byte	?
	_v12	sdword	?
	_v10	sdword	?
	_v6	sdword	?
.code
main PROC
1:		mov		dword ptr [sum],0
2:		mov		dword ptr [i],0
3:		cmp		dword ptr [i],9
4:		jl		9
5:		jmp		12
6:		mov		eax,dword ptr [i]
		add		eax,1
		mov		dword ptr [_v6],eax
7:		mov		dword ptr [i],_v6
8:		jmp		3
9:		mov		eax,dword ptr [i]
		imul	eax,5
		mov		dword ptr [_v7],eax
10:		mov		eax,4
		imul	ecx,eax,i
		mov		dword ptr b[ecx],dword ptr [_v7]
11:		jmp		6
12:		mov		dword ptr [i],0
13:		cmp		dword ptr [i],9
14:		jl		19
15:		jmp		22
16:		mov		eax,dword ptr [i]
		add		eax,1
		mov		dword ptr [_v9],eax
17:		mov		dword ptr [i],_v9
18:		jmp		13
19:		mov		eax,dword ptr [sum]
		add		eax,[b
		mov		dword ptr [_v10],eax
20:		mov		dword ptr [sum],_v10
21:		jmp		16
22:		cmp		dword ptr [i],0
23:		je		25
24:		jmp		27
25:		mov		byte ptr [a],'c'
26:		jmp		28
27:		mov		byte ptr [a],'d'
28:		mov		eax,dword ptr [sum]
		idiv	eax,9
		mov		dword ptr [_v12],eax
29:		mov		dword ptr [result],_v12
33:		invoke	printf, chr$("%d, %c\n"), result, a
34:		mov		eax,0
		ret
	main	endp

end	main
