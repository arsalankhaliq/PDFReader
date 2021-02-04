Dim objShell
Set objShell = WScript.CreateObject("WScript.shell")
scriptdir = CreateObject("Scripting.FileSystemObject").GetParentFolderName(WScript.ScriptFullName)
'WScript.echo scriptdir
Set objFSO = CreateObject("Scripting.FileSystemObject")
set objFolder = objFSO.GetFolder(scriptdir)
Set re = New RegExp
Set outFile = objFSO.CreateTextFile(scriptdir & "\total.txt", True)
fileCount = 0
x = InputBox("Enter the X position:")
y = InputBox("Enter the Y position:")
w = InputBox("Enter the width:",,"30")
h = InputBox("Enter the height:",,"10")
d = InputBox("Enter the digits:",,"5")
With re
    .Pattern = "^\d{"&d&"}$"
end With

for each f in objFSO.GetFolder(scriptdir).Files
    if InStr(f.Name, ".pdf") > 0 Or InStr(f.Name, ".PDF") > 0 Then
        fileCount = fileCount + 1
        '                       RAM limit                    JAR name                       [        PDF               ]         x     y     w     h
        objShell.run "java -jar -Xmx8g """ & scriptdir & "\PDFReader\dist\PDFReader.jar"" """ & scriptdir & "\" & f.Name & """ "&x&" "&y&" "&w&" "&h&"",1,True
        'WCOC
        ' objShell.run "java -jar -Xmx8g """ & scriptdir & "\Counts\dist\Counts.jar"" """ & scriptdir & "\" & f.Name & """ """&readFile&""" 71 128 30 10 6",1,True
        'WScript.echo "java -jar -Xmx5g """ & scriptdir & "\Counts\dist\Counts.jar"" """ & scriptdir & "\" & f.Name & """ """&readFile&""" 91 858 30 10 6"
    end if
next


for each f in objFSO.GetFolder(scriptdir).Files
    envCount = 0
    envNum = ""
    pageCount = ""
    if InStr(f.Name, ".txt") > 0 and (instr(f.Name, "total") < 1) and (instr(f.Name, ".pdf") < 1) Then
        Set readFile = objFSO.OpenTextFile(f,1)
        do until readFile.AtEndOFStream
            line = readFile.ReadLine
            if re.Test(Trim(line)) Then
                if Trim(line) <> envNum Then
                    envNum = Trim(line)
                    envCount = envCount + 1
                end if
            elseif InStr(line, "Page:") > 0 Then
                pageCount = Trim(replace(line, "|Page:",""))
            end if
        loop
        ' outFile.WriteLine(f.Name & chr(9) & envCount & chr(9) & pageCount)
        outFile.WriteLine(Replace(f.Name,".txt","") & chr(9) & chr(9) & envCount & chr(9) & chr(9) & pageCount)
    end if
next
' set totalFile = objFSO.CreateTextFile(scriptdir & "\Total.txt", True)
' for each f in objFSO.GetFolder(scriptdir).Files
'     if InStr(f.Name, ".txt") > 0 and (instr(f.Name, "Total") < 1) Then
'         set readFile = objFSO.OpenTextFile(f,1)
'         totalEnv = Trim(Split(readFile.ReadLine, ":")(1))
'         totalPgs = Trim(Split(readFile.ReadLine, ":")(1))
'         totalFile.WriteLine(Split(f.name, ".")(0) & "|" & totalEnv & "|" & totalPgs)
'     end if
' next
WScript.echo "Program has finished. Files Processed: " & fileCount
Set objShell = Nothing
Set objFSO = Nothing