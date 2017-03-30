function [out1, out2]=mySongStats(songObj);

    % sort struct according to artists
    % by first converting to cell
    Afields = fieldnames(songObj);
    Acell = struct2cell(songObj);
    sz = size(Acell);
    Acell = reshape(Acell, sz(1), []);
    Acell = Acell';
    Acell = sortrows(Acell, 2);
    Acell = reshape(Acell', sz);
    songObj = cell2struct(Acell, Afields, 1);

    prev  = songObj(1).artist;
    idx   = 1;
    count = [];
    CH    = [];
    TW    = [];
    art   = {};
    out2  = cell([1 1]);
    out2it = 1;

    count(1) = 0;
    CH(1) = 0;
    TW(1) = 0;
    
    % iterate through the list once 
    for i = 1:length(songObj)
        if strcmp(songObj(i).artist, 'unknown') | strcmp(songObj(i).artist, '??') | strcmp(songObj(i).artist, '??')
            % ignore bad artist
        else
            % same artist, add one to its count
            if strcmp(songObj(i).artist, prev)
                count(idx) = count(idx) + 1;
                % check song language
                if strcmp(songObj(i).language, 'Chinese')
                    CH(1) = 1;
                elseif strcmp(songObj(i).language, 'Taiwanese')
                    TW(1) = 1;
                end
            else   % new artist
                
                % before processing new one
                % check if this artist has both CH and TW songs
                if CH(1) == 1 & TW(1) == 1
                    out2{out2it} = art{idx};
                    out2it = out2it + 1;
                end
                
                idx  = idx + 1;
                prev =  songObj(i).artist;
                CH(1) = 0; TW(1) = 0;
                count(idx) = 1;
                art{idx} = songObj(i).artist;
                % check song language
                if strcmp(songObj(i).language, 'Chinese')
                    CH(1) = 1;
                elseif strcmp(songObj(i).language, 'Taiwanese')
                    TW(1) = 1;
                end
            end
        end
    end

    % sort count and use its sorted index(1~10) to get artists
    [count, index] = sort(count, 'Descend');

    % get top 10 artists and their data to cell
    out1cell = cell([2 11]);
    itt = 1;
    for it = 1:11
        out1cell{itt}  = art{index(it)};
        out1cell{it*2} = count(it);
        itt = itt + 2;
    end
    
    % convert cell into out1 struct
    out1fields    = cell([2 1]);
    out1fields{1} = 'name';
    out1fields{2} = 'songCount';
    out1 = cell2struct(out1cell, out1fields, 1);
end