function [] = makeICAWineQualAfter ()

dimensions = [5];
DigitICA = cell (4,length(dimensions));

data = textread('winequality-white.data', '%s', 'delimiter', ',', 'emptyvalue', 0);
data = reshape(data, 12, 4898);
A = str2double(data(1:11, :));
L = str2double(data(12, :));
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

% save DigitICA.mat DigitICA

end