function myEncrypt(inputFile, outputFile);
    [y, fs] = audioread(inputFile);
    z = y;
    for i = 1:length(y)
        if y(i) > 0
            z(i) = 1-y(i);
        elseif y(i) < 0
            z(i) = -1-y(i);
        end
    end
    z = flipud(z);
    audiowrite(outputFile, z, fs);
end