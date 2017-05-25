int main()
{
	char a;
	int score[9];
	int sum;
	int result;
	int i;
	sum = 0;
	for (i = 0;i < 9; i = i + 1)
	{
		score[i] = i * 5;
	}
	for (i = 0;i < 9; i = i + 1)
	{
		sum = sum + score[i];
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