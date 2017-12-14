function outputSignal = mySine(duration, freq);
    fs    = 16000;
    time  = (0:duration*fs-1)/fs;
    theta = freq(1)*time + 1/2*(freq(2)-freq(1))*time.^2/duration;
    outputSignal = sin(2*pi*(theta));
    %plot(time, outputSignal)
end