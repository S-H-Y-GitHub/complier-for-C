int main()
{
	char a;
	int b[9];
	int sum;
	int result;
	int i;
	sum = 0;
	for (i = 0;i < 9; i = i + 1)
	{
		b[i] = i * 5;
	}
	for (i = 0;i < 9; i = i + 1)
	{
		sum = sum + b[i];
	}
	if (i == 0)
	{
		a = 'c';
	}
	else
	{
		a = 'd';
	}
	result = sum / 9;
	printf("%d, %c\n", result, a);
	return 0;
}