//Bombeiro

+!apagarIncendio(civil)
	<- ?pos(civil,X,Y);
	apagarIncendio(X,Y);
	!apagarIncendio(civil).
+!apagarIncendio(civil).

+!apagarIncendio(policial)
	<- ?pos(policial,X,Y);
	apagarIncendio(X,Y);
	!apagarIncendio(policial).
+!apagarIncendio(policial).

+!bombeiroFogo
	<- apagarFogo;
	!bombeiroFogo.
+!bombeiroFogo.

+bombeiroFogo : true <- apagarFogo.

+bombeiroIncendiario : true
	<- !prenderIncendiario(bombeiro).
	
+!prenderIncendiario(bombeiro)
	<- .send(policial, achieve, prenderIncendiario(bombeiro)).



