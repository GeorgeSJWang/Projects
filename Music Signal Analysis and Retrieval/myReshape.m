function B=myReshape(A);;
    [n, m, p] = size(A);
    B = reshape(A,n*m,p)';
end