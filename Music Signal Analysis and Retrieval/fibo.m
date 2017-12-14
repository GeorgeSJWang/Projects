function answer_f=fibo(n);
    answer = zeros(1, n);
    answer(1) = 0;
    answer(2) = 1;
    k = 3;
    while k <= n
        answer(k) = answer(k-2) + answer(k-1);
        k = k + 1;
    end
    answer_f = answer(n);
end