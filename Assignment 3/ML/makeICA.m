function [] = makeICA ()

dimensions = [30];
DigitICA = cell (4,length(dimensions));

data = textread('spambase.data', '%s', 'delimiter', ',', 'emptyvalue', 0);
data = reshape(data, 58, 4601 );
A = str2double(data(1:57, :));
L = str2double(data(58, :));
ALL = A';
pn = ALL;

for i = 1:length(dimensions)
    display (['processing dimension ' num2str(dimensions(i))]);

    tic; 
    
    [icasig, A, W] = fastica (pn, 'lastEig', dimensions(i));
   
    clear W;
    DigitICA{3,i} = toc;
    DigitICA{1,i} = dimensions(i);
    DigitICA{2,i} = A;
    DigitICA{4,i} = max(max (corr(icasig')-eye(size(icasig,1))));

    % plot distribution of kurtosis
    figure;
    hist(kurtosis( icasig'));
    title(['Distribution of kurtosis for ' num2str(dimensions(i)) ' dimensions']);
end

save DigitICA.mat DigitICA

end